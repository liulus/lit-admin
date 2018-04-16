package com.github.lit.security.service.impl;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.commons.exception.BizException;
import com.github.lit.security.dao.AuthorityDao;
import com.github.lit.security.dao.RoleAuthorityDao;
import com.github.lit.security.dao.RoleDao;
import com.github.lit.security.dao.UserRoleDao;
import com.github.lit.security.model.*;
import com.github.lit.security.service.RoleService;
import com.github.lit.security.util.AuthorityUtils;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User : liulu
 * Date : 2017/11/19 16:42
 * version $Id: RoleServiceImpl.java, v 0.1 Exp $
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Resource
    private AuthorityDao authorityDao;

    @Resource
    private RoleAuthorityDao roleAuthorityDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Override
    public List<Role> findPageList(RoleQo roleQo) {
        return roleDao.findPageList(roleQo);
    }

    @Override
    public Role findById(Long roleId) {
        return roleDao.findById(roleId);
    }

    @Override
    public Role findByCode(String code) {
        return roleDao.findByProperty("code", code);
    }

    @Override
    public Long insert(Role role) {
        checkCode(role.getCode());
        return roleDao.insert(role);
    }

    @Override
    public void update(Role role) {
        Role oldRole = findById(role.getId());

        Optional.ofNullable(oldRole)
                .map(Role::getCode)
                .filter(code -> !Objects.equals(code, role.getCode()))
                .ifPresent(this::checkCode);
        roleDao.update(role);
    }

    private void checkCode(String code) {
        if (Strings.isNullOrEmpty(code)) {
            return;
        }
        int count = roleDao.getSelect().where(Role::getCode).equalsTo(code).count();
        if (count > 0) {
            throw new BizException("角色码已经存在");
        }
    }

    @Override
    public int delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }
        List<Role> roles = roleDao.findByIds(ids);
        List<Long> validIds = new ArrayList<>(roles.size());

        for (Role role : roles) {
            int count = authorityDao.countByRoleId(role.getId());
            if (count > 0) {
                throw new BizException(String.format("请先取消角色 %s 绑定的 %d 个权限", role.getName(), count));
            }
            validIds.add(role.getId());
        }

        return roleDao.deleteByIds(validIds.toArray(new Long[validIds.size()]));
    }

    @Override
    public void bindAuthority(Long roleId, Long[] authorityIds) {
        Role role = roleDao.findById(roleId);
        if (role == null) {
            return;
        }
        // 需要新增的有效 authId
        List<Long> newAuthIds = authorityDao.getSelect()
                .include(Authority::getId)
                .where(Authority::getId).in((Object[]) authorityIds)
                .list(Long.class);
        // 当前角色下的 旧的权限
        List<RoleAuthority> oldAuths = roleAuthorityDao.findByRoleId(roleId);

        // 对比找出需删除的 角色权限Id
        Long[] deleteRoleAuthIds = oldAuths.stream()
                .filter(oldAuth -> !newAuthIds.contains(oldAuth.getAuthorityId()))
                .map(RoleAuthority::getId)
                .toArray(Long[]::new);
        Optional.ofNullable(deleteRoleAuthIds)
                .filter(idArray -> idArray.length > 0)
                .ifPresent(idArray -> roleAuthorityDao.deleteByIds(idArray));

        // 对比找出需要新增的 authId
        List<Long> oldAuthIds = oldAuths.stream().map(RoleAuthority::getAuthorityId).collect(Collectors.toList());
        List<Long> insertAuthIds = newAuthIds.stream().filter(newAuthId -> !oldAuthIds.contains(newAuthId)).collect(Collectors.toList());

        insertAuthIds.forEach(authId -> {
            RoleAuthority roleAuthority = new RoleAuthority();
            roleAuthority.setRoleId(roleId);
            roleAuthority.setAuthorityId(authId);
            roleAuthorityDao.insert(roleAuthority);
        });
    }

    @Override
    public void bindUser(Long userId, Long[] roleIds) {
        // 需要新增的有效 roleId
        List<Long> newRoleIds = roleDao.getSelect().include(Role::getId).where(Role::getId).in((Object[]) roleIds).list(Long.class);
        // 当前用户下的 旧的角色
        List<UserRole> oldRoles = userRoleDao.findByUserId(userId);

        // 对比找出需删除的 用户角色Id
        Long[] deleteUserRoleIds = oldRoles.stream()
                .filter(oldRole -> !newRoleIds.contains(oldRole.getRoleId()))
                .map(UserRole::getId)
                .toArray(Long[]::new);
        Optional.ofNullable(deleteUserRoleIds)
                .filter(idArray -> idArray.length > 0)
                .ifPresent(idArray -> userRoleDao.deleteByIds(idArray));

        // 对比找出需要新增的 roleId
        List<Long> oldRoleIds = oldRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Long> insertRoleIds = newRoleIds.stream().filter(newRoleId -> !oldRoleIds.contains(newRoleId)).collect(Collectors.toList());

        insertRoleIds.forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleDao.insert(userRole);
        });
    }

    @Override
    public List<Role> findByUserId(Long userId) {
        return roleDao.findByUserId(userId);
    }

    @Override
    public List<AuthorityVo.TreeNode> findAuthorityTree(Long roleId) {

        List<Authority> allAuthorities = authorityDao.getSelect().list();
        if (CollectionUtils.isEmpty(allAuthorities)) {
            return Collections.emptyList();
        }

        List<AuthorityVo.TreeNode> result = new ArrayList<>();

        List<Long> roleAuthIds = roleAuthorityDao.findByRoleId(roleId).stream()
                .map(RoleAuthority::getAuthorityId)
                .collect(Collectors.toList());

        // 按 module 分组, 并设置节点是否选中
        Map<String, List<AuthorityVo.TreeNode>> moduleMap = allAuthorities.stream()
                .map(auth -> BeanUtils.convert(auth, new AuthorityVo.TreeNode()))
                .peek(auth -> auth.setChecked(roleAuthIds.contains(auth.getId())))
                .collect(Collectors.groupingBy(AuthorityVo.TreeNode::getModule));


        for (Map.Entry<String, List<AuthorityVo.TreeNode>> moduleEntry : moduleMap.entrySet()) {
            List<AuthorityVo.TreeNode> moduleEntryValue = moduleEntry.getValue();
            if (CollectionUtils.isEmpty(moduleEntryValue)) {
                continue;
            }
            AuthorityVo.TreeNode moduleNode = new AuthorityVo.TreeNode();
            moduleNode.setChecked(true);
            moduleNode.setCode(moduleEntry.getKey());
            moduleNode.setName(AuthorityUtils.getModuleName(moduleEntry.getKey()));

            // 按 function 进行分组, 并设置moduleNode 是否选中
            Map<String, List<AuthorityVo.TreeNode>> funcMap = moduleEntryValue.stream()
                    .peek(funcAuth -> {
                        if (!funcAuth.getChecked()) {
                            moduleNode.setChecked(false);
                        }
                    })
                    .filter(funcAuth -> !StringUtils.isEmpty(funcAuth.getFunction()))
                    .collect(Collectors.groupingBy(AuthorityVo.TreeNode::getFunction));

            List<AuthorityVo.TreeNode> moduleChild = new ArrayList<>();
            for (Map.Entry<String, List<AuthorityVo.TreeNode>> funcEntry : funcMap.entrySet()) {
                List<AuthorityVo.TreeNode> funcChild = funcEntry.getValue();
                if (CollectionUtils.isEmpty(funcChild)) {
                    continue;
                }
                AuthorityVo.TreeNode funcNode = new AuthorityVo.TreeNode();
                funcNode.setChecked(true);
                funcNode.setCode(funcEntry.getKey());
                funcNode.setName(AuthorityUtils.getFunctionName(funcEntry.getKey()));

                funcNode.setChildren(funcChild);
                // funcNode 子节点有一个未选中 就是未选中
                for (AuthorityVo.TreeNode treeNode : funcChild) {
                    if (!treeNode.getChecked()) {
                        funcNode.setChecked(false);
                        break;
                    }
                }
                moduleChild.add(funcNode);
            }
            // 添加所有 没有function的节点
            moduleChild.addAll(moduleEntryValue.stream()
                    .filter(funcAuth -> StringUtils.isEmpty(funcAuth.getFunction()))
                    .collect(Collectors.toList()));

            moduleNode.setChildren(moduleChild);
            result.add(moduleNode);
        }

        return result;
    }


}
