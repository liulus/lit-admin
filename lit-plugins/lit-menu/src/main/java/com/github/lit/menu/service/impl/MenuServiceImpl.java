package com.github.lit.menu.service.impl;

import com.github.lit.commons.event.Event;
import com.github.lit.commons.exception.BizException;
import com.github.lit.jdbc.JdbcTools;
import com.github.lit.jdbc.statement.select.Select;
import com.github.lit.menu.dao.MenuDao;
import com.github.lit.menu.event.MenuUpdateEvent;
import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuQo;
import com.github.lit.menu.service.MenuService;
import com.github.lit.plugin.exception.AppException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private MenuDao menuDao;

    @Resource
    private JdbcTools jdbcTools;

    @Override
    public List<Menu> findPageList(MenuQo qo) {
        return menuDao.findPageList(qo);
    }

    @Override
    public Menu findById(Long id) {
        return menuDao.findById(id);
    }

    @Override
    @Event(MenuUpdateEvent.class)
    public Long insert(Menu menu) {

        checkMenuCode(menu.getCode(), menu.getParentId());

        menu.setEnable(true);
        return menuDao.insert(menu);
    }

    @Override
    @Event(MenuUpdateEvent.class)
    public int update(Menu menu) {

        Menu old = menuDao.findById(menu.getMenuId());
        if (old == null) {
            return 0;
        }
        if (!Objects.equals(menu.getCode(), old.getCode())) {
            checkMenuCode(menu.getCode(), menu.getParentId());
        }

        return menuDao.update(menu);
    }

    private void checkMenuCode(String code, Long parentId) {
        if (parentId == null) {
            parentId = 0L;
        }
        Menu menu = menuDao.findByCodeAndParentId(code, parentId);
        if (menu != null) {
            throw new BizException("菜单编码已经存在");
        }
    }

    @Override
    @Event(MenuUpdateEvent.class)
    public int delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }

        List<Menu> menus = menuDao.findByIds(ids);
        List<Long> validIds = new ArrayList<>(menus.size());

        for (Menu menu : menus) {
            int count = menuDao.count(MenuQo.builder().parentId(menu.getMenuId()).build());
            if (count > 0) {
                throw new AppException(String.format("请先删除 %s 的子菜单数据 !", menu.getName()));
            }
            validIds.add(menu.getMenuId());
        }
        return menuDao.deleteByIds(validIds.toArray(new Long[validIds.size()]));
    }

    @Override
    @Event(MenuUpdateEvent.class)
    public void moveMenu(Long parentId, Long[] ids) {


        Arrays.sort(ids);
        // 验证新的 parentId 不是 被移动菜单本身
        if (parentId != null && Arrays.binarySearch(ids, parentId) >= 0) {
            throw new BizException("父菜单不能是自己 !");
        }

        // 验证新的 parentId 不是 被移动菜单的子菜单
        Menu menu = menuDao.findById(parentId);
        while (menu != null) {
            if (menu.getParentId() != null && Arrays.binarySearch(ids, menu.getParentId()) >= 0) {
                throw new BizException("无法移动到子菜单下 !");
            }
            menu = menuDao.findById(menu.getParentId());
        }

        menuDao.move(parentId, ids);
    }

    @Override
    @Event(MenuUpdateEvent.class)
    public void move(Long menuId, boolean isUp) {
        if (menuId == null) {
            throw new AppException("菜单id不能为空 !");
        }

        Menu menu = jdbcTools.get(Menu.class, menuId);
        if (menu == null) {
            throw new AppException("被移动菜单不存在 !");
        }

        String orderNum = "orderNum";
        Select<Menu> select = jdbcTools.select(Menu.class)
//                .where(orderNum, isUp ? Logic.LTEQ : Logic.GTEQ, menu.getOrderNum())
                .where("menuId").equalsTo(menu.getMenuId())
                .and("parentId").equalsTo(menu.getParentId());
        if (isUp) {
            select.and(orderNum).lessThanOrEqual(menu.getOrderNum());
        } else {
            select.and(orderNum).graterThanOrEqual(menu.getOrderNum());
        }

        select = isUp ? select.desc(orderNum) : select.asc(orderNum);

        Menu changeMenu = select.page(1, 1).single();
        if (changeMenu == null) {
            throw new AppException("无法移动!");
        }

        jdbcTools.createUpdate(Menu.class)
                .set(orderNum, changeMenu.getOrderNum())
                .where("menuId").equalsTo(menu.getMenuId())
                .execute();
        jdbcTools.createUpdate(Menu.class)
                .set(orderNum, menu.getOrderNum())
                .where("menuId").equalsTo(changeMenu.getMenuId())
                .execute();

    }

    @Override
    @Event(MenuUpdateEvent.class)
    public void changeStatus(Long menuId, boolean isEnable) {

        jdbcTools.createUpdate(Menu.class)
                .set("enable", isEnable)
                .where("menuId").equalsTo(menuId)
                .execute();
    }

    @Override
    public List<Menu> findAll() {
        return menuDao.findList(new MenuQo());
    }

}
