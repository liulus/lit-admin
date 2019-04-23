package com.github.lit.user.service.impl;

import com.github.lit.support.exception.BizException;
import com.github.lit.support.jdbc.JdbcRepository;
import com.github.lit.support.page.PageResult;
import com.github.lit.user.model.Organization;
import com.github.lit.user.model.OrganizationQo;
import com.github.lit.user.service.OrganizationService;
import com.github.lit.user.util.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User : liulu
 * Date : 17-10-3 下午4:29
 * version $Id: OrganizationServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Resource
    private JdbcRepository jdbcRepository;


    @Override
    public PageResult<Organization> findPageList(OrganizationQo qo) {
        return jdbcRepository.selectPageList(Organization.class, qo);
    }

    @Override
    public Organization findById(Long id) {
        return jdbcRepository.selectById(Organization.class, id);
    }

    @Override
    public Organization findByCode(String orgCode) {
        return jdbcRepository.selectByProperty(Organization::getCode, orgCode);
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
        List<Organization> organizations = jdbcRepository.selectListByProperty(Organization::getParentId, organization.getParentId());
        List<String> levelIndexes = organizations.stream().map(Organization::getLevelIndex).collect(Collectors.toList());

        organization.setLevelIndex(UserUtils.nextLevelIndex(parentLevelIndex, levelIndexes));
        jdbcRepository.insert(organization);
        return organization.getId();
    }

    @Override
    public void update(Organization organization) {

        Organization oldOrg = findById(organization.getId());

        Optional.ofNullable(oldOrg)
                .map(Organization::getCode)
                .filter(code -> !Objects.equals(code, organization.getCode()))
                .ifPresent(this::checkOrgCode);

        jdbcRepository.updateSelective(organization);
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
        List<Organization> orgs = jdbcRepository.selectByIds(Organization.class, Arrays.asList(ids));
        List<Long> validIds = new ArrayList<>(orgs.size());
        for (Organization org : orgs) {
            int count = jdbcRepository.countByProperty(Organization::getParentId, org.getParentId());
            if (count > 0) {
                throw new BizException(String.format("请先删除 %s 的子机构数据 ", org.getFullName()));
            }
            validIds.add(org.getId());
        }
        jdbcRepository.deleteByIds(Organization.class, validIds);
    }


}
