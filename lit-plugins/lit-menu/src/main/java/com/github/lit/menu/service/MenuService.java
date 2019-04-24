package com.github.lit.menu.service;

import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuQo;
import com.github.lit.menu.model.MenuVo;
import com.github.lit.support.page.PageResult;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/7/10 10:50
 * version $Id: MenuService.java, v 0.1 Exp $
 */
public interface MenuService {

    /**
     * 根据 qo 的条件查询菜单列表
     *
     * @param qo 查询条件
     * @return 菜单列表
     */
    PageResult<Menu> findPageList(MenuQo qo);

    /**
     * 根据Id查询菜单
     *
     * @param id 主键
     * @return 菜单
     */
    Menu findById(Long id);

    /**
     * 构建菜单树
     *
     * @param filterDisabled 是否过滤禁用菜单
     * @param filterEmpty 是否过滤子菜单为空并且没有url
     * @return Detail
     */
    List<MenuVo.Detail> buildMenuTree(boolean filterDisabled, boolean filterEmpty);

    /**
     * 增加菜单
     *
     * @param menu Menu
     */
    Long insert(Menu menu);

    /**
     * 更新菜单
     *
     * @param menu Menu
     */
    int update(Menu menu);

    /**
     * 删除菜单
     *
     * @param ids
     */
    int delete(Long[] ids);

    /**
     * 移动菜单
     *
     * @param parentId 新的parentId
     * @param ids      要移动的菜单 id
     */
    void moveMenu(Long parentId, Long[] ids);

    /**
     * 改变菜单状态, 启用->禁用, 禁用->启用
     *
     * @param id       id
     */
    void changeStatus(Long id);

    /**
     * @return List<Menu>
     */
    List<Menu> findAll();

    /**
     * 根据权限码查询菜单
     *
     * @param authorities 权限码
     * @return List<Menu>
     */
    List<Menu> findByAuthorities(List<String> authorities);

    /**
     * 查询当前登录用户的菜单
     *
     * @return List<Menu>
     */
    List<Menu> findMyMenus();

}
