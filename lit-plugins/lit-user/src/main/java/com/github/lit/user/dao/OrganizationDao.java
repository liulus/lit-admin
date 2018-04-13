package com.github.lit.user.dao;

import com.github.lit.plugin.dao.BaseDao;
import com.github.lit.user.model.Organization;

/**
 * User : liulu
 * Date : 2018/4/11 20:46
 * version $Id: OrganizationDao.java, v 0.1 Exp $
 */
public interface OrganizationDao extends BaseDao<Organization> {

    int countByParentId(Long parentId);

}
