package com.github.lit.security.dao;

import com.github.lit.plugin.dao.BaseDao;
import com.github.lit.security.model.Role;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/12 19:35
 * version $Id: RoleDao.java, v 0.1 Exp $
 */
public interface RoleDao extends BaseDao<Role> {

    List<Role> findByUserId(Long userId);
}
