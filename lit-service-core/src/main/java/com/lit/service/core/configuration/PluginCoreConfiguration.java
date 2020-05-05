package com.lit.service.core.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfinal.template.ext.spring.JFinalViewResolver;
import com.jfinal.template.source.ClassPathSourceFactory;
import com.lit.service.core.constant.PluginConst;
import com.lit.service.core.model.Route;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.List;
import java.util.Optional;

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

    @Bean
    public JFinalViewResolver getJFinalViewResolver() {
        JFinalViewResolver jfr = new JFinalViewResolver();
        // setDevMode 配置放在最前面
        jfr.setDevMode(true);

        // 使用 ClassPathSourceFactory 从 class path 与 jar 包中加载模板文件
        jfr.setSourceFactory(new ClassPathSourceFactory());

        // 在使用 ClassPathSourceFactory 时要使用 setBaseTemplatePath
        // 代替 jfr.setPrefix("/view/")
        JFinalViewResolver.engine.setBaseTemplatePath("/templates");

        jfr.setSuffix(".html");
        jfr.setSessionInView(true);
        return jfr;
    }

    @EventListener
    public void appStartListener(ContextRefreshedEvent contextRefreshedEvent) {

        ApplicationContext context = contextRefreshedEvent.getApplicationContext();
        JFinalViewResolver viewResolver = context.getBean(JFinalViewResolver.class);
        viewResolver.addSharedObject("singlePage", pluginProperties.getSinglePage());
        viewResolver.addSharedObject("theme", pluginProperties.getTheme());

        if (context instanceof WebApplicationContext) {
            ServletContext servletContext = ((WebApplicationContext) context).getServletContext();
            String contextPath = Optional.ofNullable(servletContext)
                    .map(ServletContext::getContextPath).orElse("");
            viewResolver.addSharedObject("contextPath", contextPath);
        }
    }

    @Bean
    public Route index() {
        return new Route("home", "/", PluginConst.HOME_VIEW);
    }

    @Controller
    public static class PluginRouteController {

        @Resource
        private PluginProperties pluginProperties;

        @Resource
        private ObjectMapper objectMapper;

        @Resource
        private List<Route> routes;

        @GetMapping({"/", "/index"})
        public String index(ModelMap model) throws JsonProcessingException {
            model.put(PluginConst.VIEW, PluginConst.HOME_VIEW);
            if (pluginProperties.getSinglePage()) {
                // 单页应用路由配置
                model.put("pluginRoutes", objectMapper.writeValueAsString(routes));
                return PluginConst.INDEX_SINGLE;
            }
            return PluginConst.INDEX_MULTI;
        }

    }


}
