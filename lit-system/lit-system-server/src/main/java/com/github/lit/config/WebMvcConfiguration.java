package com.github.lit.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author liulu
 * @version v1.0
 * created_at 2020/7/30
 */
@Configuration
@EnableConfigurationProperties(CoreProperties.class)
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private CoreProperties coreProperties;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(coreProperties.getIndexPath()).setViewName("index");
        registry.addViewController("/login").setViewName("default/login");
        registry.addViewController("/forget").setViewName("default/forget");
    }

    @EventListener
    public void appStartListener(ContextRefreshedEvent contextRefreshedEvent) throws JsonProcessingException {
        Map<String, Object> globalData = new HashMap<>();
        globalData.put("singlePage", coreProperties.getSinglePage());
        globalData.put("theme", coreProperties.getTheme());


        ApplicationContext context = contextRefreshedEvent.getApplicationContext();
        if (coreProperties.getSinglePage()) {
            // 单页应用路由配置
//            Map<String, Route> routeMap = context.getBeansOfType(Route.class);
//            viewResolver. addSharedObject("routes", objectMapper.writeValueAsString(routeMap.values()));
        }
        if (context instanceof WebApplicationContext) {
            ServletContext servletContext = ((WebApplicationContext) context).getServletContext();
            String contextPath = Optional.ofNullable(servletContext)
                    .map(ServletContext::getContextPath).orElse("");
            globalData.put("contextPath", contextPath);
        }

        MustacheViewResolver viewResolver = context.getBean(MustacheViewResolver.class);
        viewResolver.setAttributesMap(globalData);
    }

}
