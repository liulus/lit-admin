package com.github.lit.menu.dao.impl;

import com.github.lit.jdbc.statement.select.Select;
import com.github.lit.menu.dao.MenuDao;
import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuQo;
import com.github.lit.plugin.dao.AbstractBaseDao;
import com.google.common.base.Strings;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/1 15:58
 * version $Id: MenuDaoImpl.java, v 0.1 Exp $
 */
@Repository
public class MenuDaoImpl extends AbstractBaseDao<Menu, MenuQo> implements MenuDao {


    @Override
    protected void buildCondition(Select<Menu> select, MenuQo menuQo) {

        if (menuQo.getParentId() != null) {
            select.where("parentId").equalsTo(menuQo.getParentId());
        }

        if (!Strings.isNullOrEmpty(menuQo.getMenuCode())) {
            select.and("menuCode").equalsTo(menuQo.getMenuCode());
        }

        select.asc("orderNum");
    }

    @Override
    public Menu findByCodeAndParentId(String code, Long parentId) {
        return getSelect()
                .where("parentId").equalsTo(parentId)
                .and("code").equalsTo(code)
                .single();
    }

    @Override
    public List<Menu> findByIds(Long[] ids) {
        return getSelect()
                .where("menuId").in((Object[]) ids)
                .list();
    }

    @Override
    public void move(Long parentId, Long[] ids) {
        jdbcTools.createUpdate(Menu.class)
                .set("parentId", parentId)
                .where("menuId").in((Object[]) ids)
                .execute();
    }
}
