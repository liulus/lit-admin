package com.github.lit.security.dao.impl;

import com.github.lit.jdbc.statement.select.Select;
import com.github.lit.security.dao.RoleDao;
import com.github.lit.security.model.Role;
import com.github.lit.security.model.RoleQo;
import com.github.lit.security.model.UserRole;
import com.github.lit.web.dao.AbstractBaseDao;
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
    protected void buildCondition(Select<Role> select, Object obj) {
        RoleQo qo = (RoleQo) obj;

        if (qo.getUserId() != null) {
            List<Long> roleIds = getRoleIdsByUserId(qo.getUserId());
            select.and(Role::getId).in(roleIds.toArray());
        }
    }

    @Override
    public List<Role> findByUserId(Long userId) {
        List<Long> roleIds = getRoleIdsByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return getSelect().where(Role::getId).in(roleIds.toArray()).list();
    }

    private List<Long> getRoleIdsByUserId(Long userId) {
        return jdbcTools.select(UserRole.class)
                .include(UserRole::getRoleId)
                .where(UserRole::getUserId).equalsTo(userId)
                .list(Long.class);
    }
}
