package net.skeyurt.lit.commons.spring.web;

import net.skeyurt.lit.commons.context.ResultConst;
import net.skeyurt.lit.commons.exception.RunResultHolder;
import net.skeyurt.lit.commons.page.PageInfo;
import net.skeyurt.lit.commons.page.PageList;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User : liulu
 * Date : 2017/3/20 20:21
 * version $Id: ModelResultHandlerInterceptor.java, v 0.1 Exp $
 */
public class ModelResultHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RunResultHolder.clear();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        ModelMap modelMap = modelAndView == null ? null : modelAndView.getModelMap();
        if (modelMap != null) {

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


            if (RunResultHolder.hasError()) {
                modelMap.put(ResultConst.SUCCESS, false);
                String code = RunResultHolder.getCode(false);
                if (!StringUtils.isEmpty(code)) {
                    modelMap.put(ResultConst.CODE, code);
                }
                String messages = RunResultHolder.getStrMessages();
                if (!StringUtils.isEmpty(messages)) {
                    modelMap.put(ResultConst.MASSAGE, messages);
                }
            } else {
                if (modelMap.get(ResultConst.SUCCESS) == null) {
                    modelMap.put(ResultConst.SUCCESS, true);
                }
            }
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RunResultHolder.clear();
    }
}
