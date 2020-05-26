package com.lit.service.user.repository;

import com.lit.service.user.model.User;
import com.lit.support.data.jdbc.JdbcRepository;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
public interface UserRepository extends JdbcRepository<User> {
}
