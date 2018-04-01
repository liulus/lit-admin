package com.github.lit.plugin.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * User : liulu
 * Date : 17-10-3 下午1:54
 * version $Id: SpringMvcConfig.java, v 0.1 Exp $
 */
@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new ModelAttributeInterceptor()).addPathPatterns("/**");
    }

    /**
     * 自定义 controller 方法返回值处理
     *
     * @param returnValueHandlers returnValueHandlers
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new ModelAttributeHandler());
    }

    /**
     * 自定义参数解析器, 可以根据 content-type 自动解析 request body 中的 json绑定参数
     *
     * @param argumentResolvers argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new JsonBodyMethodProcessor());
    }
}
