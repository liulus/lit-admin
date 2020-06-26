package com.github.lit.repository.impl;

import com.github.lit.repository.RoleAuthorityRepository;
import com.github.lit.repository.entity.RoleAuthority;
import com.lit.support.data.jdbc.AbstractJdbcRepository;
import org.springframework.stereotype.Repository;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
@Repository
public class RoleAuthorityRepositoryImpl extends AbstractJdbcRepository<RoleAuthority> implements RoleAuthorityRepository {
}
