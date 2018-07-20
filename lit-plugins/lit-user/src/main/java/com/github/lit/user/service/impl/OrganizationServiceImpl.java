package com.github.lit.user.service.impl;

import com.github.lit.exception.BizException;
import com.github.lit.jdbc.JdbcTools;
import com.github.lit.user.dao.OrganizationDao;
import com.github.lit.user.model.Organization;
import com.github.lit.user.model.OrganizationQo;
import com.github.lit.user.service.OrganizationService;
import com.github.lit.user.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    private final OrganizationDao organizationDao;

    @Autowired
    public OrganizationServiceImpl(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }


    @Override
    public List<Organization> findPageList(OrganizationQo qo) {
        return organizationDao.findPageList(qo);
    }

    @Override
    public Organization findById(Long id) {
        return organizationDao.findById(id);
    }

    @Override
    public Organization findByCode(String orgCode) {
        return organizationDao.findByProperty("code", orgCode);
    }

    @Override
    public Long insert(Organization organization) {

        checkOrgCode(organization.getCode());

        String parentLevelIndex = Optional.ofNullable(organization.getParentId())
                .filter(parentId -> parentId > 0L)
                .map(this::findById)
                .map(Organization::getLevelIndex)
                .orElse("");

        // 处理 levelIndex
        List<String> levelIndexes = organizationDao.getSelect()
                .include(Organization::getLevelIndex)
                .where(Organization::getParentId).equalsTo(Optional.ofNullable(organization.getParentId()).orElse(0L))
                .list(String.class);

        organization.setLevelIndex(UserUtils.nextLevelIndex(parentLevelIndex, levelIndexes));

        return jdbcTools.insert(organization);
    }

    @Override
    public void update(Organization organization) {

        Organization oldOrg = findById(organization.getId());

        Optional.ofNullable(oldOrg)
                .map(Organization::getCode)
                .filter(code -> !Objects.equals(code, organization.getCode()))
                .ifPresent(this::checkOrgCode);

        organizationDao.update(organization);
    }

    private void checkOrgCode(String orgCode) {
        Organization org = findByCode(orgCode);
        if (org != null) {
            throw new BizException("机构号已经存在");
        }
    }

    @Override
    public void delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        List<Organization> orgs = organizationDao.findByIds(ids);
        List<Long> validIds = new ArrayList<>(orgs.size());
        for (Organization org : orgs) {
            int count = organizationDao.countByParentId(org.getParentId());
            if (count > 0) {
                throw new BizException(String.format("请先删除 %s 的子机构数据 ", org.getFullName()));
            }
            validIds.add(org.getId());
        }
        organizationDao.deleteByIds(validIds.toArray(new Long[validIds.size()]));
    }


}
