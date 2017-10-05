package net.skeyurt.lit.user.service;

import net.skeyurt.lit.user.entity.Organization;
import net.skeyurt.lit.user.vo.OrganizationVo;

import java.util.List;

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
    List<Organization> queryPageList(OrganizationVo vo);

    /**
     * 查询单个机构
     *
     * @param orgId
     * @return
     */
    Organization findById(Long orgId);

    Organization findByCode(String orgCode);

    void insert(Organization organization);

    void update(Organization organization);

    void delete(Long... ids);
}
