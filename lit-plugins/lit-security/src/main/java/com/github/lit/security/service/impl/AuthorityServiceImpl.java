package com.github.lit.security.service.impl;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.tool.DictionaryTools;
import com.github.lit.jdbc.JdbcTools;
import com.github.lit.plugin.exception.AppException;
import com.github.lit.security.context.SecurityConst;
import com.github.lit.security.model.*;
import com.github.lit.security.service.AuthorityService;
import com.github.lit.security.service.RoleService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User : liulu
 * Date : 2017/11/19 16:41
 * version $Id: AuthorityServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private JdbcTools jdbcTools;

    @Resource
    private RoleService roleService;


    @Override
    public List<Authority> findPageList(AuthorityQo qo) {

        return jdbcTools.select(Authority.class).page(qo).list();
    }

    @Override
    public List<AuthorityVo> findAuthorityTree() {

        List<Authority> authorities = jdbcTools.select(Authority.class).list();

        List<Dictionary> authorityTypes = DictionaryTools.findChildByRootKey(SecurityConst.AUTHORITY_TYPE);
        if (CollectionUtils.isEmpty(authorityTypes)) {
            return BeanUtils.convert(AuthorityVo.class, authorities);
        }

        List<AuthorityVo> result = new ArrayList<>();

        Map<String, List<AuthorityVo>> typeMap = authorities.stream()
                .filter(authority -> !Strings.isNullOrEmpty(authority.getAuthorityType()))
                .map(authority -> BeanUtils.convert(authority, new AuthorityVo()))
                .collect(Collectors.groupingBy(Authority::getAuthorityType));

        authorityTypes.forEach(dictionary -> {
            List<AuthorityVo> childVos = typeMap.get(dictionary.getDictKey());
            if (!CollectionUtils.isEmpty(childVos)) {
                AuthorityVo authorityVo = new AuthorityVo();
                authorityVo.setIsParent(true);
                authorityVo.setNocheck(true);
                authorityVo.setAuthorityCode(dictionary.getDictKey());
                authorityVo.setAuthorityName(dictionary.getDictValue());
                authorityVo.setChildren(childVos);
                result.add(authorityVo);
            }
        });

        // 过滤没有权限类型的
        List<AuthorityVo> other = authorities.stream()
                .filter(authority -> Strings.isNullOrEmpty(authority.getAuthorityType()))
                .map(authority -> BeanUtils.convert(authority, new AuthorityVo()))
                .collect(Collectors.toList());
        result.addAll(other);
        return result;
    }


    @Override
    public List<Authority> findByRoleId(Long roleId) {
        Role role = roleService.findById(roleId);
        if (role == null) {
            return Collections.emptyList();
        }
        return jdbcTools.select(Authority.class)
                .join(RoleAuthority.class)
                .on(Authority.class, "authorityId").equalsTo(RoleAuthority.class, "authorityId")
                .and(RoleAuthority.class, "roleId").equalsTo(role.getRoleId())
                .list();
    }

    public List<Authority> findByUserId (Long userId) {
        List<Role> roles = roleService.findByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }
        return jdbcTools.select(Authority.class)
                .join(RoleAuthority.class)
                .on(Authority.class, "authorityId").equalsTo(RoleAuthority.class, "authorityId")
                .and(RoleAuthority.class, "roleId")
                .in(roles.stream().map(Role::getRoleId).distinct().collect(Collectors.toList()).toArray())
                .list();
    }

    @Override
    public Authority findById(Long authorityId) {
        return jdbcTools.get(Authority.class, authorityId);
    }

    @Override
    public Authority findByCode(String authorityCode) {
        return jdbcTools.findByProperty(Authority.class, "authorityCode", authorityCode);
    }

    @Override
    public Long insert(Authority authority) {
        checkAuthorityCode(authority.getAuthorityCode());
        return (Long) jdbcTools.insert(authority);
    }

    @Override
    public void update(Authority authority) {

        Authority oldAuthority = findById(authority.getAuthorityId());
        if (!Objects.equals(oldAuthority.getAuthorityCode(), authority.getAuthorityCode())) {
            checkAuthorityCode(authority.getAuthorityCode());
        }
        jdbcTools.update(authority);
    }

    private void checkAuthorityCode(String authorityCode) {
        if (Strings.isNullOrEmpty(authorityCode)) {
            throw new AppException("权限码不能为空!");
        }
        int count = jdbcTools.select(Authority.class).where("authorityCode").equalsTo(authorityCode).count();
        if (count > 0) {
            throw new AppException("权限码已经存在!");
        }
    }

    @Override
    public int delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }

        return jdbcTools.deleteByIds(Authority.class, ids);
    }


}
