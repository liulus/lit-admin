package com.github.lit.security.service.impl;

import com.github.lit.commons.exception.BizException;
import com.github.lit.security.dao.AuthorityDao;
import com.github.lit.security.dao.RoleAuthorityDao;
import com.github.lit.security.dao.RoleDao;
import com.github.lit.security.model.Authority;
import com.github.lit.security.model.Role;
import com.github.lit.security.model.RoleAuthority;
import com.github.lit.security.model.RoleQo;
import com.github.lit.security.service.RoleService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    public List<Role> findByUserId(Long userId) {
        return roleDao.findByUserId(userId);
    }


}
