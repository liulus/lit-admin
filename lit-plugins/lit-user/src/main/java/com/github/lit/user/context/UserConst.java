package com.github.lit.user.context;

import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.tool.DictionaryTools;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * User : liulu
 * Date : 17-10-3 下午4:36
 * version $Id: UserConst.java, v 0.1 Exp $p
 */
public class UserConst {

    // session 中登录用户 key
    public static final String LOGIN_USER = "_login_user";

    // 超级管理员
    public static final String ADMIN = "admin";

    public static final String PWD_PREFIX = "@$#l#y!^";

    public static final String ORGANIZATION_TYPE = "organization_type";


    public static Map<String, Dictionary> getOrgType() {
        return OrgTypeHolder.ORG_TYPE_MAP;
    }

    private static class OrgTypeHolder {
        private static final Map<String, Dictionary> ORG_TYPE_MAP;

        static {
            List<Dictionary> childByRootKey = DictionaryTools.findChildByRootKey(ORGANIZATION_TYPE);

            ORG_TYPE_MAP = Maps.uniqueIndex(childByRootKey, new Function<Dictionary, String>() {
                @Override
                public String apply(Dictionary dictionary) {
                    return dictionary.getDictKey();
                }
            });

        }
    }

}
