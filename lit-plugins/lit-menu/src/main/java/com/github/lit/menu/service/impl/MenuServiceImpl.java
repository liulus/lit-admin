package com.github.lit.menu.service.impl;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.commons.event.Event;
import com.github.lit.jdbc.JdbcTools;
import com.github.lit.jdbc.enums.Logic;
import com.github.lit.jdbc.statement.Select;
import com.github.lit.menu.event.MenuUpdateEvent;
import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuQo;
import com.github.lit.menu.model.MenuVo;
import com.github.lit.menu.service.MenuService;
import com.github.lit.plugin.exception.AppException;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2017/7/13 19:53
 * version $Id: MenuServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Resource
    private JdbcTools jdbcTools;

    @Override
    public List<Menu> findPageList(MenuQo qo) {
        return buildSelect(qo).page(qo).list();
    }

    @Override
    public Menu findById(Long id) {
        return jdbcTools.get(Menu.class, id);
    }

    private Select<Menu> buildSelect(MenuQo qo) {
        Select<Menu> select = jdbcTools.createSelect(Menu.class).where("parentId", qo.getParentId());

        if (!Strings.isNullOrEmpty(qo.getMenuCode())) {
            select.and("menuCode", qo.getMenuCode());
        }

        select.asc("orderNum");

        return select;
    }

    @Override
    @Event(eventClass = MenuUpdateEvent.class)
    public void add(MenuVo vo) {

        Menu oldMenu = findByCodeAndParentId(vo.getMenuCode(), vo.getParentId());
        if (oldMenu != null) {
            throw new AppException("菜单编码已经存在!");
        }

        Menu menu = BeanUtils.convert(new Menu(), vo);
        menu.setEnable(true);

        Integer maxOrder = jdbcTools.createSelect(Menu.class)
                .addFunc("max", "orderNum")
                .where("parentId", vo.getParentId())
                .single(int.class);

        menu.setOrderNum(maxOrder == null ? 1 : maxOrder + 1);

        jdbcTools.insert(menu);
    }

    @Override
    @Event(eventClass = MenuUpdateEvent.class)
    public void update(MenuVo vo) {
        Menu oldMenu = jdbcTools.get(Menu.class, vo.getMenuId());
        if (!Objects.equals(oldMenu.getMenuCode(), vo.getMenuCode())) {
            Menu menu = findByCodeAndParentId(vo.getMenuCode(), vo.getParentId());
            if (menu != null) {
                throw new AppException("菜单编码已经存在!");
            }
        }

        jdbcTools.update(BeanUtils.convert(new Menu(), vo));
    }

    private Menu findByCodeAndParentId(String menuCode, Long parentId) {

        MenuQo menuQo = MenuQo.builder().menuCode(menuCode).parentId(parentId).build();

        return buildSelect(menuQo).single();
    }

    @Override
    @Event(eventClass = MenuUpdateEvent.class)
    public void delete(Long... ids) {
        if (ids == null || ids.length == 0) {
            return;
        }
        List<Long> validIds = new ArrayList<>(ids.length);
        for (Long id : ids) {
            Menu menu = findById(id);
            if (menu == null) {
                continue;
            }
            int count = buildSelect(MenuQo.builder().parentId(menu.getMenuId()).build()).count();
            if (count > 0) {
                throw new AppException(String.format("请先删除 %s 的子菜单数据 !", menu.getMenuName()));
            }
            validIds.add(id);
        }

        jdbcTools.deleteByIds(Menu.class, validIds.toArray(new Serializable[validIds.size()]));
    }

    @Override
    @Event(eventClass = MenuUpdateEvent.class)
    public void moveMenu(Long parentId, Long[] ids) {


        Arrays.sort(ids);
        // 验证新的 parentId 不是 被移动菜单本身
        if (parentId != null && Arrays.binarySearch(ids, parentId) >= 0) {
            throw new AppException("父菜单不能是自己 !");
        }

        // 验证新的 parentId 不是 被移动菜单的子菜单
        Menu menu = jdbcTools.get(Menu.class, parentId);
        while (menu != null) {
            if (menu.getParentId() != null && Arrays.binarySearch(ids, menu.getParentId()) >= 0) {
                throw new AppException("无法移动到子菜单下 !");
            }
            menu = jdbcTools.get(Menu.class, menu.getParentId());
        }

        // 执行更新
        jdbcTools.createUpdate(Menu.class)
                .set("parentId", parentId)
                .where("menuId", Logic.IN, (Object[]) ids)
                .execute();
    }

    @Override
    @Event(eventClass = MenuUpdateEvent.class)
    public void move(Long menuId, boolean isUp) {
        if (menuId == null) {
            throw new AppException("菜单id不能为空 !");
        }

        Menu menu = jdbcTools.get(Menu.class, menuId);
        if (menu == null) {
            throw new AppException("被移动菜单不存在 !");
        }

        String orderNum = "orderNum";
        Select<Menu> select = jdbcTools.createSelect(Menu.class)
                .where(orderNum, isUp ? Logic.LTEQ : Logic.GTEQ, menu.getOrderNum())
                .and("menuId", Logic.NOT_EQ, menu.getMenuId())
                .and("parentId", menu.getParentId());

        select = isUp ? select.desc(orderNum) : select.asc(orderNum);

        Menu changeMenu = select.page(1, 1).single();
        if (changeMenu == null) {
            throw new AppException("无法移动!");
        }

        jdbcTools.createUpdate(Menu.class)
                .set(orderNum, changeMenu.getOrderNum())
                .where("menuId", menu.getMenuId())
                .execute();
        jdbcTools.createUpdate(Menu.class)
                .set(orderNum, menu.getOrderNum())
                .where("menuId", changeMenu.getMenuId())
                .execute();

    }

    @Override
    @Event(eventClass = MenuUpdateEvent.class)
    public void changeStatus(Long menuId, boolean isEnable) {

        jdbcTools.createUpdate(Menu.class)
                .set("enable", isEnable)
                .where("menuId", menuId)
                .execute();
    }

    @Override
    public List<MenuVo> findAll() {
        List<Menu> rootMenus = buildSelect(new MenuQo()).list();
        List<MenuVo> menuVos = BeanUtils.convert(MenuVo.class, rootMenus);

        findChildMenu(menuVos);

        return menuVos;
    }

    private void findChildMenu(List<MenuVo> menuVos) {
        MenuQo condition = new MenuQo();

        for (MenuVo menuVo : menuVos) {
            condition.setParentId(menuVo.getMenuId());
            List<Menu> childMenus = buildSelect(condition).list();
            if (!childMenus.isEmpty()) {
                menuVo.setIsParent(true);
                List<MenuVo> childMenuVos = BeanUtils.convert(MenuVo.class, childMenus);
                menuVo.setChildren(childMenuVos);
                findChildMenu(childMenuVos);
            } else {
                menuVo.setIsParent(false);
            }
        }
    }
}
