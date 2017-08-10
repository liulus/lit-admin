package net.skeyurt.lit.menu.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import net.skeyurt.lit.commons.bean.BeanUtils;
import net.skeyurt.lit.commons.bean.ConvertCallBack;
import net.skeyurt.lit.commons.event.Event;
import net.skeyurt.lit.commons.exception.AppCheckedException;
import net.skeyurt.lit.dictionary.entity.Dictionary;
import net.skeyurt.lit.dictionary.tool.DictionaryTools;
import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.jdbc.enums.Logic;
import net.skeyurt.lit.jdbc.sta.Select;
import net.skeyurt.lit.menu.context.MenuConst;
import net.skeyurt.lit.menu.entity.Menu;
import net.skeyurt.lit.menu.event.MenuUpdateEvent;
import net.skeyurt.lit.menu.service.MenuService;
import net.skeyurt.lit.menu.vo.MenuVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * User : liulu
 * Date : 2017/7/13 19:53
 * version $Id: MenuServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional()
public class MenuServiceImpl implements MenuService {

    @Resource
    private JdbcTools jdbcTools;

    @Override
    public List<MenuVo> queryPageList(MenuVo vo) {
        Select<Menu> select = buildSelect(vo);

        final List<Dictionary> dictionaries = DictionaryTools.findChildByRootKey(MenuConst.MENU_TYPE);

        final Map<String, Dictionary> dictMap = Maps.uniqueIndex(dictionaries, new Function<Dictionary, String>() {
            @Override
            public String apply(Dictionary dictionary) {
                return dictionary.getDictKey();
            }
        });

        return BeanUtils.convert(MenuVo.class, select.page(vo).list(), new ConvertCallBack<MenuVo, Menu>() {
            @Override
            public void convertCallBack(MenuVo target, Menu source) {
                Dictionary dictionary = dictMap.get(source.getMenuType());
                target.setMenuTypeStr(dictionary == null ? "" : dictionary.getDictValue());

                MenuVo menuVo = MenuVo.builder().parentId(source.getMenuId()).build();
                int count = buildSelect(menuVo).count();
                target.setIsParent(count > 0);
            }
        });
    }

    @Override
    public MenuVo findById(Long id) {
        return BeanUtils.convert(new MenuVo(), jdbcTools.get(Menu.class, id));
    }

    private Select<Menu> buildSelect(MenuVo vo) {
        Select<Menu> select = jdbcTools.createSelect(Menu.class).where("parentId", vo.getParentId());

        if (!Strings.isNullOrEmpty(vo.getMenuCode())) {
            select.and("menuCode", vo.getMenuCode());
        }

        select.asc("orderNum");

        return select;
    }

    @Override
    @Event(eventClass = MenuUpdateEvent.class)
    public void add(MenuVo vo) {

        checkMenuCode(vo.getMenuCode(), vo.getParentId());

        Menu menu = BeanUtils.convert(new Menu(), vo);
        menu.setEnable(true);

        Integer maxOrder = jdbcTools.createSelect(Menu.class)
                .addFunc("max", "orderNum")
                .where("parentId", vo.getParentId())
                .single(int.class);

        menu.setOrderNum(maxOrder == null ? 1 : maxOrder + 1);

        vo.setMenuId((Long) jdbcTools.insert(menu));
    }

    @Override
    @Event(eventClass = MenuUpdateEvent.class)
    public void update(MenuVo vo) {
        checkMenuCode(vo.getMenuCode(), vo.getParentId());
        jdbcTools.update(BeanUtils.convert(new Menu(), vo));
    }

    private void checkMenuCode(String menuCode, Long parentId) {
        Menu menu = findByCodeAndParentId(menuCode, parentId);
        if (menu != null) {
            throw new AppCheckedException("菜单编码已经存在!");
        }
    }

    private Menu findByCodeAndParentId(String menuCode, Long parentId) {

        MenuVo menuVo = MenuVo.builder().menuCode(menuCode).parentId(parentId).build();

        return buildSelect(menuVo).single();
    }

    @Override
    @Event(eventClass = MenuUpdateEvent.class)
    public void delete(Long... ids) {
        jdbcTools.deleteByIds(Menu.class, (Serializable[]) ids);
    }

    @Override
    @Event(eventClass = MenuUpdateEvent.class)
    public void moveMenu(Long parentId, Long[] ids) {


        Arrays.sort(ids);
        // 验证新的 parentId 不是 被移动菜单本身
        if (parentId != null && Arrays.binarySearch(ids, parentId) >= 0) {
            throw new AppCheckedException("父菜单不能是自己 !");
        }

        // 验证新的 parentId 不是 被移动菜单的子菜单
        Menu menu = jdbcTools.get(Menu.class, parentId);
        while (menu != null) {
            if (menu.getParentId() != null && Arrays.binarySearch(ids, menu.getParentId()) >= 0) {
                throw new AppCheckedException("无法移动到子菜单下 !");
            }
            menu = jdbcTools.get(Menu.class, menu.getParentId());
        }

        // 执行更新
        jdbcTools.createUpdate(Menu.class)
                .set("parentId")
                .values(parentId)
                .where("menuId", Logic.IN, (Object[]) ids)
                .execute();
    }

    @Override
    public void move(Long menuId, boolean isUp) {
        if (menuId == null) {
            throw new AppCheckedException("菜单id不能为空 !");
        }

        Menu menu = jdbcTools.get(Menu.class, menuId);
        if (menu == null) {
            throw new AppCheckedException("被移动菜单不存在 !");
        }

        String orderNum = "orderNum";
        Select<Menu> select = jdbcTools.createSelect(Menu.class)
                .where(orderNum, isUp ? Logic.LTEQ : Logic.GTEQ, menu.getOrderNum())
                .and("menuId", Logic.NOT_EQ, menu.getMenuId())
                .and("parentId", menu.getParentId());

        select = isUp ? select.desc(orderNum) : select.asc(orderNum);

        Menu changeMenu = select.page(1, 1).single();
        if (changeMenu == null) {
            throw new AppCheckedException("无法移动!");
        }

        jdbcTools.createUpdate(Menu.class)
                .set(orderNum)
                .values(changeMenu.getOrderNum())
                .where("menuId", menu.getMenuId())
                .execute();
        jdbcTools.createUpdate(Menu.class)
                .set(orderNum)
                .values(menu.getOrderNum())
                .where("menuId", changeMenu.getMenuId())
                .execute();

    }

    @Override
    @Event(eventClass = MenuUpdateEvent.class)
    public void changeStatus(Long menuId, boolean isEnable) {

        jdbcTools.createUpdate(Menu.class)
                .set("enable")
                .values(isEnable)
                .where("menuId", menuId)
                .execute();
    }

    @Override
    public List<MenuVo> findAll() {
        List<Menu> rootMenus = buildSelect(new MenuVo()).list();
        List<MenuVo> menuVos = BeanUtils.convert(MenuVo.class, rootMenus);

        findChildMenu(menuVos);

        return menuVos;
    }

    private void findChildMenu(List<MenuVo> menuVos) {
        MenuVo condition = new MenuVo();

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
