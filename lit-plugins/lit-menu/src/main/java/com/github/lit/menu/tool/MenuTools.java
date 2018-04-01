package com.github.lit.menu.tool;

import com.github.lit.commons.bean.BeanUtils;
import com.github.lit.commons.spring.SpringContextUtils;
import com.github.lit.menu.model.Menu;
import com.github.lit.menu.model.MenuVo;
import com.github.lit.menu.service.MenuService;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * User : liulu
 * Date : 2017/7/14 19:35
 * version $Id: MenuTools.java, v 0.1 Exp $
 */
public class MenuTools {

    private static final MenuService MENU_SERVICE = SpringContextUtils.getBean(MenuService.class);

    public static List<MenuVo.Detail> findAll() {
        List<Menu> allMenus = MENU_SERVICE.findAll();
        List<MenuVo.Detail> rootMenus = allMenus.stream()
                .filter(menu -> menu.getParentId() == 0L)
                .map(menu -> BeanUtils.convert(menu, new MenuVo.Detail()))
                .collect(Collectors.toList());

        Map<Long, List<MenuVo.Detail>> menuMap = allMenus.stream()
                .filter(menu -> menu.getParentId() != 0L)
                .map(menu -> BeanUtils.convert(menu, new MenuVo.Detail()))
                .collect(Collectors.groupingBy(MenuVo.Detail::getParentId));

        setChildMenu(rootMenus, menuMap);

        return rootMenus;
    }

    private static void setChildMenu(List<MenuVo.Detail> parentMenus, Map<Long, List<MenuVo.Detail>> menuMap) {
        if (CollectionUtils.isEmpty(parentMenus)) {
            return;
        }
        for (MenuVo.Detail menu : parentMenus) {
            List<MenuVo.Detail> children = menuMap.get(menu.getMenuId());
            if (CollectionUtils.isEmpty(children)) {
                menu.setIsParent(false);
            } else {
                menu.setIsParent(true);
                menu.setChildren(children);
                setChildMenu(children, menuMap);
            }
        }
    }

}
