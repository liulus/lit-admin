package com.github.lit.security.dao;

import com.github.lit.plugin.dao.BaseDao;
import com.github.lit.security.model.Authority;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/12 19:36
 * version $Id: AuthorityDao.java, v 0.1 Exp $
 */
public interface AuthorityDao extends BaseDao<Authority> {

    List<Authority> findByRoleId(Long roleId);

    int countByRoleId(Long roleId);

}
