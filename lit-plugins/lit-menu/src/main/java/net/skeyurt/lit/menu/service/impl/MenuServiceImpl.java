package net.skeyurt.lit.menu.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import net.skeyurt.lit.commons.bean.BeanUtils;
import net.skeyurt.lit.commons.bean.ConvertCallBack;
import net.skeyurt.lit.commons.exception.AppCheckedException;
import net.skeyurt.lit.dictionary.entity.Dictionary;
import net.skeyurt.lit.dictionary.tool.DictionaryTools;
import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.jdbc.enums.Logic;
import net.skeyurt.lit.jdbc.sta.Select;
import net.skeyurt.lit.menu.context.MenuConst;
import net.skeyurt.lit.menu.entity.Menu;
import net.skeyurt.lit.menu.service.MenuService;
import net.skeyurt.lit.menu.vo.MenuVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * User : liulu
 * Date : 2017/7/13 19:53
 * version $Id: MenuServiceImpl.java, v 0.1 Exp $
 */
@Service
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
    @Transactional
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
    @Transactional
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
    public void delete(Long... ids) {
        jdbcTools.deleteByIds(Menu.class, (Serializable[]) ids);
    }

    @Override
    public void moveMenu(Long parentId, Long[] ids) {
        jdbcTools.createUpdate(Menu.class)
                .set("parentId")
                .values(parentId)
                .where("menuId", Logic.IN, (Object[]) ids)
                .execute();
    }
}
