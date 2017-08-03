package net.skeyurt.lit.menu.tool;

import net.skeyurt.lit.commons.context.SpringContextUtils;
import net.skeyurt.lit.menu.service.MenuService;
import net.skeyurt.lit.menu.vo.MenuVo;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/7/14 19:35
 * version $Id: MenuTools.java, v 0.1 Exp $
 */
public class MenuTools {

    private static final MenuService MENU_SERVICE = SpringContextUtils.getBean(MenuService.class);

    public static List<MenuVo> findAll() {
        return MENU_SERVICE.findAll();
    }


}
