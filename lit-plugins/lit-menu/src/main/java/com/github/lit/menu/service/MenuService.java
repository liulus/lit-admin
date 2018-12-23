package com.github.lit.menu.service;

import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuQo;
import com.github.lit.support.page.Page;

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
    Page<Menu> findPageList(MenuQo qo);

    /**
     * 根据Id查询菜单
     *
     * @param id 主键
     * @return 菜单
     */
    Menu findById(Long id);

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
     * 改变菜单状态
     *
     * @param id       id
     * @param isEnable 是否启用
     */
    void changeStatus(Long id, boolean isEnable);

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
