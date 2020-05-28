package com.lit.service.user.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User : liulu
 * Date : 2018/4/13 17:55
 * version $Id: AuthorityUtils.java, v 0.1 Exp $
 */
public abstract class AuthorityUtils {

    private static final Map<String, String> MODULE_MAP = new ConcurrentHashMap<>();

    private static final Map<String, String> FUNCTION_MAP = new ConcurrentHashMap<>();

    static {
        MODULE_MAP.put("system", "系统管理");

        FUNCTION_MAP.put("dictionary_manager", "字典管理");
        FUNCTION_MAP.put("menu_manager", "菜单管理");
        FUNCTION_MAP.put("param_manager", "参数管理");
        FUNCTION_MAP.put("user_manager", "用户管理");
        FUNCTION_MAP.put("org_manager", "组织管理");
        FUNCTION_MAP.put("role_manager", "角色管理");
    }

    public static String getModuleName(String module) {
        return MODULE_MAP.get(module);
    }

    public static String getFunctionName(String function) {
        return FUNCTION_MAP.get(function);
    }

    public static void putModule(String module, String name) {
        MODULE_MAP.put(module, name);
    }

    public static void putFunction(String function, String name){
        FUNCTION_MAP.put(function, name);
    }



}
