package com.github.lit.user.dao.impl;

import com.github.lit.jdbc.statement.select.Select;
import com.github.lit.plugin.core.dao.AbstractBaseDao;
import com.github.lit.user.dao.OrganizationDao;
import com.github.lit.user.model.Organization;
import com.github.lit.user.model.OrganizationQo;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;

/**
 * User : liulu
 * Date : 2018/4/11 20:47
 * version $Id: OrganizationDaoImpl.java, v 0.1 Exp $
 */
@Repository
public class OrganizationDaoImpl extends AbstractBaseDao<Organization> implements OrganizationDao {


    @Override
    protected void buildCondition(Select<Organization> select, Object obj) {
        OrganizationQo qo = (OrganizationQo) obj;

        select.where(Organization::getParentId).equalsTo(qo.getParentId());

        if (!Strings.isNullOrEmpty(qo.getKeyword())) {
            select.and().bracket(Organization::getCode).like(qo.getKeyword())
                    .or(Organization::getFullName).like(qo.getKeyword())
                    .or(Organization::getRemark).equalsTo(qo.getKeyword())
                    .end();
        }

        if (!Strings.isNullOrEmpty(qo.getOrgCode())) {
            select.and(Organization::getCode).equalsTo(qo.getOrgCode());
        }
    }

    @Override
    public int countByParentId(Long parentId) {
        return getSelect()
                .where(Organization::getParentId).equalsTo(parentId)
                .count();
    }
}
