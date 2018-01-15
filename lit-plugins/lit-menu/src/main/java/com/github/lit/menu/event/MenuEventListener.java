package com.github.lit.menu.event;

import com.github.lit.commons.event.AppStartedEvent;
import com.github.lit.commons.event.EventComponent;
import com.github.lit.menu.context.MenuConst;
import com.github.lit.menu.model.MenuVo;
import com.github.lit.menu.tool.MenuTools;
import com.github.lit.plugin.web.WebUtils;
import com.google.common.eventbus.Subscribe;

import java.util.List;

/**
 * User : liulu
 * Date : 2017/8/9 21:34
 * version $Id: MenuEventListener.java, v 0.1 Exp $
 */
@EventComponent
public class MenuEventListener {

    @Subscribe
    public void appStartedEvent(AppStartedEvent event) {

        List<MenuVo> menuVos = MenuTools.findAll();
        WebUtils.setContextAttribute(MenuConst.MENUS, menuVos);
    }

    @Subscribe
    public void menuUpdateListener(MenuUpdateEvent event) {
        WebUtils.removeContextAttribute(MenuConst.MENUS);

        List<MenuVo> menuVos = MenuTools.findAll();
        WebUtils.setContextAttribute(MenuConst.MENUS, menuVos);
    }



}
