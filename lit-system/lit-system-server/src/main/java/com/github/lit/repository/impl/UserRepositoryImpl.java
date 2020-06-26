package com.github.lit.repository.impl;

import com.github.lit.repository.UserRepository;
import com.github.lit.repository.entity.User;
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
