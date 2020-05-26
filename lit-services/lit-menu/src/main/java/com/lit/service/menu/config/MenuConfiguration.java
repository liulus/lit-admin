package com.lit.service.menu.config;

import com.lit.service.core.model.Route;
import com.lit.service.core.util.PluginUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author liulu
 * @version v1.0
 * date 2019-04-23
 */
@Configuration
@ComponentScan(basePackages = "com.lit.service.menu", excludeFilters = @ComponentScan.Filter(Configuration.class))
public class MenuConfiguration {

    private static final String INDEX_PATH = "/menu/index";
    private static final String INDEX_VIEW = "/views/menu.js";

    @ConditionalOnMissingBean(name = "menuIndex")
    public Route menuIndex(){
        return new Route(INDEX_PATH, INDEX_VIEW);
    }

//    @Controller
    public static class MenuRouteController {

        @GetMapping(INDEX_PATH)
        public String index(ModelMap map) {
            return PluginUtils.addView(map, INDEX_VIEW);
        }

    }

}


