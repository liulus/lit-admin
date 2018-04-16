package com.github.lit.plugin.core.event;

import com.github.lit.commons.event.AppStartedEvent;
import com.github.lit.commons.event.EventComponent;
import com.github.lit.plugin.core.util.PluginUtils;
import com.github.lit.plugin.web.WebUtils;
import com.google.common.eventbus.Subscribe;

/**
 * User : liulu
 * Date : 2018/4/16 17:14
 * version $Id: PluginEventListener.java, v 0.1 Exp $
 */
@EventComponent
public class PluginEventListener {

    @Subscribe
    public void appStartedEvent(AppStartedEvent event) {
        WebUtils.setContextAttribute("security_present", PluginUtils.isSecurityPresent());
    }


}
