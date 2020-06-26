package com.github.lit.repository.impl;

import com.github.lit.repository.AuthorityRepository;
import com.github.lit.repository.entity.Authority;
import com.lit.support.data.jdbc.AbstractJdbcRepository;
import org.springframework.stereotype.Repository;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/26
 */
@Repository
public class AuthorityRepositoryImpl extends AbstractJdbcRepository<Authority> implements AuthorityRepository {

}
