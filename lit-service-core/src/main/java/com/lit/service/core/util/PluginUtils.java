package com.lit.service.core.util;

import com.lit.service.core.constant.PluginConst;
import com.lit.service.core.model.LoginUser;
import com.lit.support.util.ClassUtils;

import java.util.Map;

/**
 * User : liulu
 * Date : 2018/4/14 22:34
 * version $Id: PluginUtils.java, v 0.1 Exp $
 */
public abstract class PluginUtils {

    private static final boolean DICTIONARY_PRESENT = ClassUtils.isPresent("com.github.lit.dictionary.model.Dictionary");

    private static final boolean PARAM_PRESENT = ClassUtils.isPresent("com.github.lit.param.model.Param");

    private static final boolean MENU_PRESENT = ClassUtils.isPresent("com.github.lit.menu.model.Menu");

    private static final boolean USER_PRESENT = ClassUtils.isPresent("com.github.lit.user.model.User");

    private static final boolean SECURITY_PRESENT = ClassUtils.isPresent("com.github.lit.security.model.Role");


    public static boolean isDictionaryPresent() {
        return DICTIONARY_PRESENT;
    }

    public static boolean isMenuPresent() {
        return MENU_PRESENT;
    }

    public static boolean isParamPresent() {
        return PARAM_PRESENT;
    }

    public static boolean isSecurityPresent() {
        return SECURITY_PRESENT;
    }

    public static boolean isUserPresent() {
        return USER_PRESENT;
    }

    public static LoginUser getLoginUser() {
        return null;
    }


    public static String addView(Map<String, Object> model, String view ) {
        model.put(PluginConst.VIEW, view);
        return PluginConst.INDEX_MULTI;
    }



}
