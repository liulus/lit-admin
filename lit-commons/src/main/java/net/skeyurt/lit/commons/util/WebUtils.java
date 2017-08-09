package net.skeyurt.lit.commons.util;

import javax.servlet.ServletContext;

/**
 * User : liulu
 * Date : 2017/8/9 21:22
 * version $Id: WebUtils.java, v 0.1 Exp $
 */
public class WebUtils {


    private static ServletContext SERVLET_CONTEXT;

    public static ServletContext getServletContext() {
        return SERVLET_CONTEXT;
    }

    public static void setServletContext(ServletContext servletContext) {
        SERVLET_CONTEXT = servletContext;
    }

    public static Object getAttribute(String name) {
        return SERVLET_CONTEXT.getAttribute(name);
    }

    public static void setAttribute(String name, Object object) {
        SERVLET_CONTEXT.setAttribute(name, object);
    }

    public static void removeAttribute(String name) {
        SERVLET_CONTEXT.removeAttribute(name);
    }


}
