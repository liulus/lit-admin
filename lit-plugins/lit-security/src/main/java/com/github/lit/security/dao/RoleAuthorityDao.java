package com.github.lit.security.dao;

import com.github.lit.plugin.core.dao.BaseDao;
import com.github.lit.security.model.RoleAuthority;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/12 20:24
 * version $Id: RoleAuthorityDao.java, v 0.1 Exp $
 */
public interface RoleAuthorityDao extends BaseDao<RoleAuthority> {

    List<RoleAuthority> findByRoleId(Long roleId);
}
