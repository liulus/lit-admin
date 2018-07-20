package com.github.lit.security.dao;

import com.github.lit.plugin.core.dao.BaseDao;
import com.github.lit.security.model.Authority;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/12 19:36
 * version $Id: AuthorityDao.java, v 0.1 Exp $
 */
public interface AuthorityDao extends BaseDao<Authority> {

    List<Authority> findByRoleIds(Long... roleIds);

    int countByRoleId(Long roleId);

}
