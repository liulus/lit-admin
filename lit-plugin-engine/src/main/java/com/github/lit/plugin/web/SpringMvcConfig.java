package com.github.lit.plugin.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * User : liulu
 * Date : 17-10-3 下午1:54
 * version $Id: SpringMvcConfig.java, v 0.1 Exp $
 */
@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @PostConstruct
    public void initJsonAndFormArgumentResolver() {

        JsonAndFormArgumentResolver jsonAndFormArgumentResolver = null;
        ModelAttributeMethodProcessor modelAttributeMethodProcessor = null;
        RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor = null;

        for (HandlerMethodArgumentResolver argumentResolver : requestMappingHandlerAdapter.getArgumentResolvers()) {
            if (argumentResolver instanceof JsonAndFormArgumentResolver) {
                jsonAndFormArgumentResolver = (JsonAndFormArgumentResolver) argumentResolver;
                continue;
            }
            if (argumentResolver instanceof RequestResponseBodyMethodProcessor) {
                requestResponseBodyMethodProcessor = (RequestResponseBodyMethodProcessor) argumentResolver;
                continue;
            }
            if (argumentResolver instanceof ModelAttributeMethodProcessor) {
                modelAttributeMethodProcessor = (ModelAttributeMethodProcessor) argumentResolver;
            }
        }
        if (jsonAndFormArgumentResolver != null) {
            jsonAndFormArgumentResolver.setModelAttributeMethodProcessor(modelAttributeMethodProcessor);
            jsonAndFormArgumentResolver.setRequestResponseBodyMethodProcessor(requestResponseBodyMethodProcessor);
        }
    }

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
        argumentResolvers.add(new JsonAndFormArgumentResolver());
    }


}
