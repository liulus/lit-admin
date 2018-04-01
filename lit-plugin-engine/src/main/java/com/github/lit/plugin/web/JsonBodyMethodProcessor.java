package com.github.lit.plugin.web;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/1 13:49
 * version $Id: JsonBodyMethodProcessor.java, v 0.1 Exp $
 */
public class JsonBodyMethodProcessor extends RequestResponseBodyMethodProcessor {

    private static final List<HttpMessageConverter<?>> CONVERTERS = new LinkedList<>();

    static {
        CONVERTERS.add(new MappingJackson2HttpMessageConverter());
    }

    public JsonBodyMethodProcessor() {
        super(CONVERTERS);
    }

    public JsonBodyMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    public JsonBodyMethodProcessor(List<HttpMessageConverter<?>> converters, ContentNegotiationManager manager) {
        super(converters, manager);
    }

    public JsonBodyMethodProcessor(List<HttpMessageConverter<?>> converters, List<Object> requestResponseBodyAdvice) {
        super(converters, requestResponseBodyAdvice);
    }

    public JsonBodyMethodProcessor(List<HttpMessageConverter<?>> converters, ContentNegotiationManager manager, List<Object> requestResponseBodyAdvice) {
        super(converters, manager, requestResponseBodyAdvice);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        HttpServletRequest request = WebUtils.getRequest();
        if (HttpMethod.GET.matches(request.getMethod().toUpperCase())) {
            return false;
        }
        if (request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            return true;
        }
        return super.supportsParameter(parameter);
    }
}
