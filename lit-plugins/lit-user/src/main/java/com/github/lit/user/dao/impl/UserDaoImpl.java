package com.github.lit.user.dao.impl;

import com.github.lit.jdbc.statement.select.Select;
import com.github.lit.plugin.core.dao.AbstractBaseDao;
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
public class UserDaoImpl extends AbstractBaseDao<User> implements UserDao {

    @Override
    protected void buildCondition(Select<User> select, Object obj) {
        UserQo qo = (UserQo) obj;

        if (qo.getId() != null) {
            select.and(User::getId).equalsTo(qo.getId());
        }
    }
}
