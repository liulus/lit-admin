package com.github.lit.security.dao.impl;

import com.github.lit.plugin.core.dao.AbstractBaseDao;
import com.github.lit.security.dao.RoleAuthorityDao;
import com.github.lit.security.model.RoleAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/12 20:24
 * version $Id: RoleAuthorityDaoImpl.java, v 0.1 Exp $
 */
@Repository
public class RoleAuthorityDaoImpl extends AbstractBaseDao<RoleAuthority> implements RoleAuthorityDao {


    @Override
    public List<RoleAuthority> findByRoleId(Long roleId) {
        return getSelect().where(RoleAuthority::getRoleId).equalsTo(roleId).list();
    }
}
