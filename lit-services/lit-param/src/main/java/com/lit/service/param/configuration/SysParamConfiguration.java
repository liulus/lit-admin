package com.lit.service.param.configuration;

import com.lit.service.core.model.Route;
import com.lit.service.core.util.PluginUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author liulu
 * @version v1.0
 * date 2019-04-23
 */
@Configuration
public class SysParamConfiguration {

    private static final String INDEX_PATH = "/param/index";
    private static final String INDEX_VIEW = "/views/param.js";

    @Bean
    public Route paramIndex(){
        return new Route(INDEX_PATH, INDEX_VIEW);
    }

    @Controller
    public static class ParamRouteController {

        @GetMapping(INDEX_PATH)
        public String index(ModelMap map) {
            return PluginUtils.addView(map, INDEX_VIEW);
        }

    }


}


