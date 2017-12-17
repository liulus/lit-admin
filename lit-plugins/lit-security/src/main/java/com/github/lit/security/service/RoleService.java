package com.github.lit.security.service;

import com.github.lit.security.entity.Role;
import com.github.lit.security.vo.RoleVo;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/11/19 16:42
 * version $Id: RoleService.java, v 0.1 Exp $
 */
public interface RoleService {

    /**
     * 分页查询角色列表
     *
     * @param roleVo 查询条件
     * @return 角色列表
     */
    List<Role> findPageList(RoleVo roleVo);

    /**
     * 根据 roleId 查询角色
     *
     * @param roleId 角色Id
     * @return 角色
     */
    Role findById(Long roleId);

    /**
     * 根据 roleCode 查询角色
     *
     * @param roleCode 角色code
     * @return 角色
     */
    Role findByCode(String roleCode);

    /**
     * 插入一条角色记录
     *
     * @param role 角色
     * @return 角色Id
     */
    Long insert(Role role);

    /**
     * 更新角色记录
     *
     * @param role 角色
     */
    void update(Role role);

    /**
     * 根据id 删除权限记录
     *
     * @param ids 权限Id
     * @return 受影响记录数
     */
    int delete(Long[] ids);
}
