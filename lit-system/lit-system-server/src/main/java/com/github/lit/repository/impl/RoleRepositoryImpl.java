package com.github.lit.repository.impl;

import com.github.lit.repository.RoleRepository;
import com.github.lit.repository.entity.Role;
import com.lit.support.data.jdbc.AbstractJdbcRepository;
import org.springframework.stereotype.Repository;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
@Repository
public class RoleRepositoryImpl extends AbstractJdbcRepository<Role> implements RoleRepository {
}
