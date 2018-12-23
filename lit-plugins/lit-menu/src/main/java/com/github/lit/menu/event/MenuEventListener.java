package com.github.lit.menu.event;

import com.github.lit.menu.context.MenuConst;
import com.github.lit.menu.model.MenuVo;
import com.github.lit.menu.tool.MenuTools;
import com.github.lit.plugin.core.event.user.LoginEvent;
import com.github.lit.plugin.core.util.PluginUtils;
import com.github.lit.support.event.AppStartedEvent;
import com.github.lit.support.event.EventComponent;
import com.github.lit.support.util.WebUtils;
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
        if (PluginUtils.isSecurityPresent()) {
            return;
        }
        List<MenuVo.Detail> menus = MenuTools.findAll();
        WebUtils.setContextAttribute(MenuConst.MENUS, menus);
    }

    @Subscribe
    public void menuUpdateListener(MenuUpdateEvent event) {
        if (PluginUtils.isSecurityPresent()) {
            return;
        }
        WebUtils.removeContextAttribute(MenuConst.MENUS);
        List<MenuVo.Detail> menus = MenuTools.findAll();
        WebUtils.setContextAttribute(MenuConst.MENUS, menus);
    }

    @Subscribe
    public void userLoginListener(LoginEvent event){
        if (PluginUtils.isSecurityPresent()) {
            List<MenuVo.Detail> menus = MenuTools.findMyMenus();
            WebUtils.setSessionAttribute(MenuConst.MENUS, menus);
        }
    }

}
