package com.lit.service.user.repository.impl;

import com.lit.service.user.model.User;
import com.lit.service.user.repository.UserRepository;
import com.lit.support.data.jdbc.AbstractJdbcRepository;
import org.springframework.stereotype.Repository;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
@Repository
public class UserRepositoryImpl extends AbstractJdbcRepository<User> implements UserRepository {
}
