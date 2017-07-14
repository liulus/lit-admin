package net.skeyurt.lit.menu.service.impl;

import net.skeyurt.lit.commons.bean.BeanUtils;
import net.skeyurt.lit.commons.exception.AppCheckedException;
import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.jdbc.sta.Select;
import net.skeyurt.lit.menu.entity.Menu;
import net.skeyurt.lit.menu.service.MenuService;
import net.skeyurt.lit.menu.vo.MenuVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

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
    public List<MenuVo> queryPageList(MenuVo vo) {
        Select<Menu> select = jdbcTools.createSelect(Menu.class);
        processSelect(vo, select);

        return BeanUtils.convert(MenuVo.class, select.page(vo).list());
    }

    @Override
    public MenuVo findById(Long id) {
        return BeanUtils.convert(new MenuVo(), jdbcTools.get(Menu.class, id));
    }

    private void processSelect(MenuVo vo, Select<Menu> select) {

    }

    @Override
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
        return jdbcTools.createSelect(Menu.class)
                .where("menuCode", menuCode)
                .and("parentId", parentId)
                .single();
    }

    @Override
    public void delete(Long... ids) {
        jdbcTools.deleteByIds(Menu.class, (Serializable[]) ids);
    }
}
