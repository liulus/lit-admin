package com.github.lit.menu.service;

import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuQo;
import com.github.lit.menu.model.MenuVo;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/7/10 10:50
 * version $Id: MenuService.java, v 0.1 Exp $
 */
public interface MenuService {

    /**
     * 根据 vo 的条件查询菜单列表
     *
     * @param qo 查询条件
     * @return 菜单列表
     */
    List<MenuVo> findPageList(MenuQo qo);

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
     * @param vo
     */
    void add(MenuVo vo);

    /**
     * 更新菜单
     *
     * @param vo
     */
    void update(MenuVo vo);

    /**
     * 删除菜单
     *
     * @param ids
     */
    void delete(Long... ids);

    /**
     * 移动菜单
     *
     * @param parentId 新的parentId
     * @param ids      要移动的菜单 id
     */
    void moveMenu(Long parentId, Long[] ids);

    /**
     * 向上或向下移动菜单
     *
     * @param menuId 被移动菜单Id
     * @param isUp   是否向上
     */
    void move(Long menuId, boolean isUp);

    /**
     * 改变菜单状态
     *
     * @param menuId
     * @param isEnable 是否启用
     */
    void changeStatus(Long menuId, boolean isEnable);

    /**
     * @return
     */
    List<MenuVo> findAll();

}
