package net.skeyurt.lit.menu.event;

import com.google.common.eventbus.Subscribe;
import net.skeyurt.lit.commons.event.AppStartedEvent;
import net.skeyurt.lit.commons.event.EventComponent;
import net.skeyurt.lit.commons.util.WebUtils;
import net.skeyurt.lit.menu.context.MenuConst;
import net.skeyurt.lit.menu.tool.MenuTools;
import net.skeyurt.lit.menu.vo.MenuVo;

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
        WebUtils.setAttribute(MenuConst.MENUS, menuVos);
    }

    @Subscribe
    public void menuUpdateListener(MenuUpdateEvent event) {
        WebUtils.removeAttribute(MenuConst.MENUS);

        List<MenuVo> menuVos = MenuTools.findAll();
        WebUtils.setAttribute(MenuConst.MENUS, menuVos);
    }



}
