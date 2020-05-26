package com.lit.service.security.repository.impl;

import com.lit.service.security.model.RoleAuthority;
import com.lit.service.security.repository.RoleAuthorityRepository;
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
