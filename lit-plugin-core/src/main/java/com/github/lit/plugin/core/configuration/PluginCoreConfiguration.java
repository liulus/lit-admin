package com.github.lit.plugin.core.configuration;

import com.github.lit.plugin.core.constant.PluginConst;
import com.github.lit.plugin.core.context.PluginRouteContext;
import com.github.lit.plugin.core.model.Route;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-13
 */
@ComponentScan("com.github.lit")
@Configuration
@EnableConfigurationProperties(PluginProperties.class)
public class PluginCoreConfiguration {

    @Resource
    private PluginProperties pluginProperties;

//    @Bean
//    public JFinalViewResolver getJFinalViewResolver() {
//        JFinalViewResolver jfr = new JFinalViewResolver();
//        // setDevMode 配置放在最前面
//        jfr.setDevMode(true);
//
//        // 使用 ClassPathSourceFactory 从 class path 与 jar 包中加载模板文件
//        jfr.setSourceFactory(new ClassPathSourceFactory());
//
//        // 在使用 ClassPathSourceFactory 时要使用 setBaseTemplatePath
//        // 代替 jfr.setPrefix("/view/")
//        JFinalViewResolver.engine.setBaseTemplatePath("/templates");
//
//        jfr.setSuffix(".html");
//        jfr.setSessionInView(true);
//        return jfr;
//    }

    private static final String HOME_VIEW = "/views/home.js";

    @EventListener
    public void appStartListener(ContextRefreshedEvent contextRefreshedEvent) {

        ApplicationContext context = contextRefreshedEvent.getApplicationContext();
//        JFinalViewResolver viewResolver = context.getBean(JFinalViewResolver.class);
//        viewResolver.addSharedObject("singlePage", pluginProperties.getSinglePage());
//        viewResolver.addSharedObject("theme", pluginProperties.getTheme());

//        if (context instanceof WebApplicationContext) {
//            ServletContext servletContext = ((WebApplicationContext) context).getServletContext();
//            String contextPath = Optional.ofNullable(servletContext)
//                    .map(ServletContext::getContextPath).orElse("");
//            viewResolver.addSharedObject("contextPath", contextPath);
//        }

        Route route = new Route("home", "/", HOME_VIEW);
        PluginRouteContext.addRoute(route);
    }

    @Controller
    public static class PluginRouteController {

        @Resource
        private PluginProperties pluginProperties;

        @GetMapping({"/", "/index"})
        public String index(ModelMap model, HttpSession session) {
            model.put(PluginConst.VIEW, HOME_VIEW);

            return pluginProperties.getSinglePage() ? PluginConst.INDEX_SINGLE : PluginConst.INDEX_MULTI;
        }

        @ResponseBody
        @GetMapping("/api/plugin/route")
        public List<Route> getPluginRoutes() {
            return PluginRouteContext.getPluginRoutes();
        }

    }


}
