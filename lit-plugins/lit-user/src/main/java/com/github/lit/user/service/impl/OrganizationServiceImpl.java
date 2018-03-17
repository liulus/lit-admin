package com.github.lit.user.service.impl;

import com.github.lit.jdbc.JdbcTools;
import com.github.lit.jdbc.statement.select.Select;
import com.github.lit.plugin.exception.AppException;
import com.github.lit.user.model.Organization;
import com.github.lit.user.model.OrganizationQo;
import com.github.lit.user.service.OrganizationService;
import com.github.lit.user.util.UserUtils;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User : liulu
 * Date : 17-10-3 下午4:29
 * version $Id: OrganizationServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Resource
    private JdbcTools jdbcTools;


    @Override
    public List<Organization> queryPageList(OrganizationQo vo) {
        return buildSelect(vo).page(vo).list();
    }

    @Override
    public Organization findById(Long orgId) {
        return jdbcTools.get(Organization.class, orgId);
    }

    @Override
    public Organization findByCode(String orgCode) {
        return jdbcTools.findByProperty(Organization.class, "orgCode", orgCode);
    }

    @Override
    public void insert(Organization organization) {

        String parentSerialNum = "";

        Organization oldOrg = findByCode(organization.getOrgCode());
        if (oldOrg != null) {
            throw new AppException("机构号已经存在!");
        }

        // 处理 orgLevel
        if (organization.getParentId() == null) {
            organization.setOrgLevel(1);
        } else {
            Organization parentOrg = findById(organization.getParentId());
            if (parentOrg == null) {
                throw new AppException("父机构信息丢失!");
            }
            parentSerialNum = parentOrg.getSerialNum();
            organization.setOrgLevel(parentOrg.getOrgLevel() + 1);
        }

        // 处理 serialNum
        List<String> serialNums = jdbcTools.select(Organization.class)
                .include("serialNum")
                .where("parentId").equalsTo(organization.getParentId())
                .list(String.class);

        organization.setSerialNum(UserUtils.nextSerialNum(parentSerialNum, serialNums));

        jdbcTools.insert(organization);
    }

    @Override
    public void update(Organization organization) {

        Organization oldOrg = findById(organization.getOrgId());
        if (!Objects.equals(oldOrg.getOrgCode(), organization.getOrgCode())) {
            Organization checkOrg = findByCode(organization.getOrgCode());
            if (checkOrg != null) {
                throw new AppException("机构号已经存在!");
            }
        }
        // 不允许更新的属性
        organization.setOrgLevel(null);
        organization.setSerialNum(null);

        jdbcTools.update(organization);
    }

    @Override
    public void delete(Long... ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        List<Long> validIds = new ArrayList<>(ids.length);
        for (Long id : ids) {
            Organization organization = findById(id);
            if (organization == null) {
                continue;
            }
            int count = buildSelect(OrganizationQo.builder().parentId(organization.getOrgId()).build()).count();
            if (count > 0) {
                throw new AppException(String.format("请先删除 %s 的子机构数据 !", organization.getOrgName()));
            }
            validIds.add(id);
        }
        jdbcTools.deleteByIds(Organization.class, validIds.toArray(new Serializable[validIds.size()]));
    }

    private Select<Organization> buildSelect(OrganizationQo qo) {
        Select<Organization> select = jdbcTools.select(Organization.class).where("parentId").equalsTo(qo.getParentId());

        if (!Strings.isNullOrEmpty(qo.getKeyword())) {
            select.and().bracket("orgCode").like(qo.getKeyword())
                    .or("orgName").like(qo.getKeyword())
                    .or("memo").equalsTo(qo.getKeyword())
                    .end();
        }

        if (!Strings.isNullOrEmpty(qo.getOrgCode())) {
            select.and("orgCode").equalsTo(qo.getOrgCode());
        }

        return select;
    }

}
