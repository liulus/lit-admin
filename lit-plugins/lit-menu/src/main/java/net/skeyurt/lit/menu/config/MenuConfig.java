package net.skeyurt.lit.menu.config;

import net.skeyurt.lit.menu.context.MenuConst;
import net.skeyurt.lit.menu.tool.MenuTools;
import net.skeyurt.lit.menu.vo.MenuVo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/8/2 21:57
 * version $Id: MenuConfig.java, v 0.1 Exp $
 */
@Configuration
public class MenuConfig {


    @EventListener
    public void findMenu(ContextRefreshedEvent event) {
        WebApplicationContext webApplicationContext = (WebApplicationContext) event.getSource();
        ServletContext servletContext = webApplicationContext.getServletContext();
        List<MenuVo> menus = MenuTools.findAll();
        servletContext.setAttribute(MenuConst.MENUS, menus);
    }




}
