package com.github.lit.plugin.core.event;

import com.github.lit.plugin.core.util.PluginUtils;
import com.github.lit.spring.event.AppStartedEvent;
import com.github.lit.spring.event.EventComponent;
import com.github.lit.spring.util.WebUtils;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Value;

/**
 * User : liulu
 * Date : 2018/4/16 17:14
 * version $Id: PluginEventListener.java, v 0.1 Exp $
 */
@EventComponent
public class PluginEventListener {

    @Value("${lit.render.header:true}")
    private Boolean renderHeader;

    @Value("${lit.page.header:/fragment/top-nav.ftl}")
    private String pageHeader;

    @Value("${lit.render.left:true}")
    private Boolean renderLeft;

    @Value("${lit.page.left:/fragment/left-menu.ftl}")
    private String pageLeft;

    @Subscribe
    public void appStartedEvent(AppStartedEvent event) {
        WebUtils.setContextAttribute("securityPresent", PluginUtils.isSecurityPresent());

        WebUtils.setContextAttribute("renderHeader", renderHeader);
        WebUtils.setContextAttribute("pageHeader", pageHeader);
        WebUtils.setContextAttribute("renderLeft", renderLeft);
        WebUtils.setContextAttribute("pageLeft", pageLeft);
    }


}
