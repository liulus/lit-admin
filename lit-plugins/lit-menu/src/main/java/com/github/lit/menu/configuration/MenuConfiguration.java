package com.github.lit.menu.configuration;

import com.github.lit.plugin.core.context.PluginRouteContext;
import com.github.lit.plugin.core.model.Route;
import com.github.lit.plugin.core.util.PluginUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author liulu
 * @version v1.0
 * date 2019-04-23
 */
@Configuration
public class MenuConfiguration {

    private static final String INDEX_PATH = "/menu/index";
    private static final String INDEX_VIEW = "/views/menu.js";

    @EventListener
    public void appStartListener(ContextRefreshedEvent contextRefreshedEvent) {
        Route route = new Route(INDEX_PATH, INDEX_VIEW);
        PluginRouteContext.addRoute(route);
    }

    @Controller
    public static class DictionaryRouteController {

        @GetMapping(INDEX_PATH)
        public String index(ModelMap map) {
            return PluginUtils.addView(map, INDEX_VIEW);
        }


    }

}


