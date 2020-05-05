package com.lit.service.dictionary.configuration;

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
 * date 2019-03-01 15:37
 */
@Configuration
public class DictionaryConfiguration {

    private static final String INDEX = "/dictionary/index";

    private static final String INDEX_VIEW = "/views/dictionary.js";
    private static final String DETAIL_VIEW = "/views/dictionary-detail.js";

    @Bean
    public Route dictIndex(){
        return new Route(INDEX, INDEX_VIEW);
    }
    @Bean
    public Route dictDetail(){
        return new Route("/dictionary/:id", DETAIL_VIEW);
    }

    @Controller
    public static class DictionaryRouteController {

        @GetMapping(INDEX)
        public String index(ModelMap map) {
            return PluginUtils.addView(map, INDEX_VIEW);
        }

        @GetMapping("/dictionary/{id}")
        public String dictDetail(ModelMap map) {
            return PluginUtils.addView(map, DETAIL_VIEW);
        }


    }






}
