package com.lit.commons.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * User : liulu
 * Date : 2017-2-21 18:22
 * version $Id: ApplicationContextUtils.java, v 0.1 Exp $
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static <T> T getBean(Class<T> clazz, Object... objects) {
        return context.getBean(clazz, objects);
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static Object getBean(String name, Object... objects) {
        return context.getBean(name, objects);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return context.getBeansOfType(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public ApplicationContext getApplicationContext(){
        return context;
    }
}
