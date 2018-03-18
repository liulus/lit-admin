package com.github.lit.plugin.web;

import com.github.lit.commons.context.ResultConst;
import com.github.lit.commons.page.PageInfo;
import com.github.lit.commons.page.PageList;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * User : liulu
 * Date : 2017/3/20 20:21
 * version $Id: ModelAttributeInterceptor.java, v 0.1 Exp $
 */
public class ModelAttributeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        // 处理自定义视图名称
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            ViewName viewName = method.getAnnotation(ViewName.class);
            if (viewName != null) {
                modelAndView.setViewName(viewName.value());
            }
        }
        // 处理分页信息
        ModelMap modelMap = modelAndView == null ? null : modelAndView.getModelMap();
        PageInfo pageInfo = null;
        for (Object obj : modelMap.values()) {
            if (obj instanceof PageList) {
                pageInfo = ((PageList) obj).getPageInfo();
                break;
            }
        }
        if (pageInfo != null) {
            modelMap.put(ResultConst.PAGE_INFO, pageInfo);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
