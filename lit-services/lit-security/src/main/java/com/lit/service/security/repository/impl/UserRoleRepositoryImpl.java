package com.lit.service.security.repository.impl;

import com.lit.service.security.model.UserRole;
import com.lit.service.security.repository.UserRoleRepository;
import com.lit.support.data.jdbc.AbstractJdbcRepository;
import org.springframework.stereotype.Repository;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
@Repository
public class UserRoleRepositoryImpl extends AbstractJdbcRepository<UserRole> implements UserRoleRepository {
}
