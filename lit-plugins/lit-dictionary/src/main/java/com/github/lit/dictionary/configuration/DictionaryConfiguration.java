package com.github.lit.dictionary.configuration;

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
 * date 2019-03-01 15:37
 */
@Configuration
public class DictionaryConfiguration {

    private static final String INDEX_PATH = "/dictionary/index";

    private static final String INDEX_VIEW = "/views/dictionary.js";
    private static final String DETAIL_VIEW = "/views/dictionary-detail.js";


    @EventListener
    public void appStartListener(ContextRefreshedEvent contextRefreshedEvent) {
        Route route = new Route(INDEX_PATH, INDEX_VIEW);
        Route route2 = new Route("/dictionary/:id", DETAIL_VIEW);
        PluginRouteContext.addRoute(route);
        PluginRouteContext.addRoute(route2);
    }

    @Controller
    public static class DictionaryRouteController {

        @GetMapping(INDEX_PATH)
        public String index(ModelMap map) {
            return PluginUtils.addView(map, INDEX_VIEW);
        }

        @GetMapping("/dictionary/{id}")
        public String dictDetail(ModelMap map) {
            return PluginUtils.addView(map, DETAIL_VIEW);
        }


    }






}
