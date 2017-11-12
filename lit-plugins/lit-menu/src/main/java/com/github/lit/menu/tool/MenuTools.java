package com.github.lit.menu.tool;

import com.github.lit.commons.context.SpringContextUtils;
import com.github.lit.menu.service.MenuService;
import com.github.lit.menu.vo.MenuVo;

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
