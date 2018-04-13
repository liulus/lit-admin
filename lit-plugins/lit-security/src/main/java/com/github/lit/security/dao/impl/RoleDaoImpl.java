package com.github.lit.security.dao.impl;

import com.github.lit.plugin.dao.AbstractBaseDao;
import com.github.lit.security.dao.RoleDao;
import com.github.lit.security.model.Role;
import com.github.lit.security.model.UserRole;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/12 19:44
 * version $Id: RoleDaoImpl.java, v 0.1 Exp $
 */
@Repository
public class RoleDaoImpl extends AbstractBaseDao<Role> implements RoleDao {


    @Override
    public List<Role> findByUserId(Long userId) {
        List<Long> roleIds = jdbcTools.select(UserRole.class)
                .include(UserRole::getRoleId)
                .where(UserRole::getUserId).equalsTo(userId)
                .list(Long.class);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return getSelect().where(Role::getId).in(roleIds.toArray()).list();
    }
}
