package com.github.lit.security.dao.impl;

import com.github.lit.plugin.core.dao.AbstractBaseDao;
import com.github.lit.security.dao.AuthorityDao;
import com.github.lit.security.model.Authority;
import com.github.lit.security.model.RoleAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/12 19:44
 * version $Id: AuthorityDaoImpl.java, v 0.1 Exp $
 */
@Repository
public class AuthorityDaoImpl extends AbstractBaseDao<Authority> implements AuthorityDao {


    @Override
    public List<Authority> findByRoleIds(Long[] roleIds) {

        List<Long> authIds = jdbcTools.select(RoleAuthority.class)
                .include(RoleAuthority::getAuthorityId)
                .where(RoleAuthority::getRoleId).in((Object[]) roleIds)
                .list(Long.class);
        if (CollectionUtils.isEmpty(authIds)) {
            return Collections.emptyList();
        }
        return getSelect().where(Authority::getId).in(authIds.toArray()).list();
    }

    @Override
    public int countByRoleId(Long roleId) {
        return jdbcTools.select(RoleAuthority.class)
                .where(RoleAuthority::getRoleId).equalsTo(roleId)
                .count();
    }
}
