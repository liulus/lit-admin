package com.github.lit.constant;

/**
 * User : liulu
 * Date : 2017/7/10 10:16
 * version $Id: PluginConst.java, v 0.1 Exp $
 */
public abstract class PluginConst {

    private PluginConst() {
    }

    // 插件统一表前缀
    public static final String TABLE_PREFIX = "lit_";

    public static final String URL_PREFIX = "/plugin";

    public static final String REDIRECT = "redirect:" + URL_PREFIX;

    // session 中登录用户 key
    public static final String LOGIN_USER = "_login_user";

    public static final String VIEW = "view";

    public static final String HOME_VIEW = "/views/home.js";

    public static final String INDEX_MULTI = "index-multi";

    public static final String INDEX_SINGLE = "index-single";


}
