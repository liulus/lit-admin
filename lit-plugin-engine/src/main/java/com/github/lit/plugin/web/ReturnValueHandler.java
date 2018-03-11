package com.github.lit.plugin.web;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * User : liulu
 * Date : 2018/3/1 11:40
 * version $Id: ReturnValueHandler.java, v 0.1 Exp $
 */
@Component
public class ReturnValueHandler implements HandlerMethodReturnValueHandler {

    public ReturnValueHandler() {
        System.out.println("HandlerMethodReturnValueHandler");
    }



    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return true;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        System.out.println("HandlerMethodReturnValueHandler");
    }
}
