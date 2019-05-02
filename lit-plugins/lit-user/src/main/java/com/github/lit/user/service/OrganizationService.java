package com.github.lit.user.service;

import com.github.lit.support.page.PageResult;
import com.github.lit.user.model.Organization;
import com.github.lit.user.model.OrganizationQo;
import com.github.lit.user.model.OrganizationVo;

/**
 * User : liulu
 * Date : 17-10-3 下午4:29
 * version $Id: OrganizationService.java, v 0.1 Exp $
 */
public interface OrganizationService {
    /**
     * 分页查询机构数据
     *
     * @param vo 查询条件
     * @return
     */
    PageResult<Organization> findPageList(OrganizationQo vo);

    /**
     * 以企业为根, 构建组织树
     *
     * @return 整个企业的组织树
     */
    OrganizationVo.Detail buildOrgTree();

    /**
     * 查询单个机构
     *
     * @param id
     * @return
     */
    Organization findById(Long id);

    Organization findByCode(String orgCode);

    Long insert(Organization organization);

    void update(Organization organization);

    void delete(Long[] ids);

}
