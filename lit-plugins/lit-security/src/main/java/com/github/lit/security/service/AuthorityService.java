package com.github.lit.security.service;

import com.github.lit.security.model.Authority;
import com.github.lit.security.model.AuthorityQo;
import com.github.lit.security.model.AuthorityVo;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/11/19 16:41
 * version $Id: AuthorityService.java, v 0.1 Exp $
 */
public interface AuthorityService {

    /**
     * 分页查询权限列表
     *
     * @param qo 查询条件
     * @return 权限列表
     */
    List<Authority> findPageList(AuthorityQo qo);

    List<AuthorityVo> findAuthorityTree();

    /**
     * 根据角色Id查询权限列表
     *
     * @param roleId 角色Id
     * @return 权限列表
     */
    List<Authority> findByRoleId(Long roleId);

    /**
     * 根据 authorityId 查询权限
     *
     * @param authorityId 权限Id
     * @return 权限
     */
    Authority findById(Long authorityId);

    /**
     * 根据权限码查询权限
     *
     * @param authorityCode 权限码
     * @return 权限
     */
    Authority findByCode(String authorityCode);

    /**
     * 插入一条权限记录
     *
     * @param authority 权限
     * @return 权限Id
     */
    Long insert(Authority authority);

    /**
     * 更新权限记录
     *
     * @param authority 权限
     */
    void update(Authority authority);

    /**
     * 根据id 删除权限记录
     *
     * @param ids 权限Id
     * @return 受影响记录数
     */
    int delete(Long[] ids);
}
