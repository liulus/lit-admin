package com.github.lit.security.dao.impl;

import com.github.lit.security.dao.UserRoleDao;
import com.github.lit.security.model.UserRole;
import com.github.lit.web.dao.AbstractBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/16 16:54
 * version $Id: UserRoleDaoImpl.java, v 0.1 Exp $
 */
@Repository
public class UserRoleDaoImpl extends AbstractBaseDao<UserRole> implements UserRoleDao {

    @Override
    public List<UserRole> findByUserId(Long userId) {
        return getSelect().where(UserRole::getUserId).equalsTo(userId).list();
    }
}
