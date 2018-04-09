package com.github.lit.user.dao.impl;

import com.github.lit.jdbc.statement.select.Select;
import com.github.lit.plugin.dao.AbstractBaseDao;
import com.github.lit.user.dao.UserDao;
import com.github.lit.user.model.User;
import com.github.lit.user.model.UserQo;
import org.springframework.stereotype.Repository;

/**
 * User : liulu
 * Date : 2018/4/9 20:11
 * version $Id: UserDaoImpl.java, v 0.1 Exp $
 */
@Repository
public class UserDaoImpl extends AbstractBaseDao<User, UserQo> implements UserDao {

    @Override
    protected void buildCondition(Select<User> select, UserQo qo) {

        if (qo.getUserId() != null) {
            select.and(User::getUserId).equalsTo(qo.getUserId());
        }
    }
}
