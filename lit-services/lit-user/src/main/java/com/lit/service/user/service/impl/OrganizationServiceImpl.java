package com.lit.service.user.service.impl;

import com.lit.service.user.model.Organization;
import com.lit.service.user.model.OrganizationQo;
import com.lit.service.user.model.OrganizationVo;
import com.lit.service.user.repository.OrgRepository;
import com.lit.service.user.service.OrganizationService;
import com.lit.service.user.util.UserUtils;
import com.lit.support.data.domain.Page;
import com.lit.support.exception.BizException;
import com.lit.support.util.bean.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
    private OrgRepository orgRepository;


    @Override
    public Page<Organization> findPageList(OrganizationQo qo) {
        return orgRepository.selectPageList(qo);
    }

    @Override
    public OrganizationVo.Detail buildOrgTree() {
        List<Organization> organizations = orgRepository.selectAll();
        if (CollectionUtils.isEmpty(organizations)) {
            return null;
        }
        Map<Long, List<OrganizationVo.Detail>> orgLevelMap = organizations.stream()
                .map(org -> BeanUtils.convert(org, new OrganizationVo.Detail()))
                .collect(Collectors.groupingBy(OrganizationVo.Detail::getParentId));
        // 企业信息, 作为根节点只有一个
        List<OrganizationVo.Detail> rootOrg = orgLevelMap.get(0L);
        if (CollectionUtils.isEmpty(rootOrg)) {
            return null;
        }
        setChildren(rootOrg, orgLevelMap);
        return rootOrg.iterator().next();
    }

    private void setChildren(List<OrganizationVo.Detail> parents, Map<Long, List<OrganizationVo.Detail>> nodeMap) {
        if (CollectionUtils.isEmpty(parents)) {
            return;
        }
        for (OrganizationVo.Detail detail : parents) {
            List<OrganizationVo.Detail> children = nodeMap.get(detail.getId());
            if (!CollectionUtils.isEmpty(children)) {
                detail.setChildren(children);
                setChildren(children, nodeMap);
            }
        }
    }

    @Override
    public Organization findById(Long id) {
        return orgRepository.selectById(id);
    }

    @Override
    public Organization findByCode(String orgCode) {
        return orgRepository.selectByProperty(Organization::getCode, orgCode);
    }

    private Organization findRootOrg() {
        return orgRepository.selectByProperty(Organization::getParentId, 0L);
    }

    @Override
    public Long insert(Organization organization) {

        checkOrgCode(organization.getCode());

        Organization parentOrg = organization.getParentId() == null || organization.getParentId() == 0L ?
                findRootOrg() : findById(organization.getParentId());
        if (parentOrg == null) {
            throw new BizException("不存在父节点" + organization.getParentId());
        }

        // 处理 levelIndex
        List<Organization> organizations = orgRepository.selectListByProperty(Organization::getParentId, parentOrg.getId());
        List<String> levelIndexes = organizations.stream().map(Organization::getLevelIndex).collect(Collectors.toList());

        organization.setParentId(parentOrg.getId());
        organization.setLevelIndex(UserUtils.nextLevelIndex(parentOrg.getLevelIndex(), levelIndexes));
        orgRepository.insert(organization);
        return organization.getId();
    }

    @Override
    public void update(Organization organization) {

        Organization oldOrg = findById(organization.getId());

        Optional.ofNullable(oldOrg)
                .map(Organization::getCode)
                .filter(code -> !Objects.equals(code, organization.getCode()))
                .ifPresent(this::checkOrgCode);

        orgRepository.updateSelective(organization);
    }

    private void checkOrgCode(String orgCode) {
        if (StringUtils.isEmpty(orgCode)) {
            return;
        }
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
        List<Organization> orgs = orgRepository.selectByIds(Arrays.asList(ids));
        List<Long> validIds = new ArrayList<>(orgs.size());
        for (Organization org : orgs) {
            int count = orgRepository.countByProperty(Organization::getParentId, org.getParentId());
            if (count > 0) {
                throw new BizException(String.format("请先删除 %s 的子机构数据 ", org.getFullName()));
            }
            validIds.add(org.getId());
        }
        orgRepository.deleteByIds(validIds);
    }


}
