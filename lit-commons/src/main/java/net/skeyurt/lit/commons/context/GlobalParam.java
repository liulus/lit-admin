package net.skeyurt.lit.commons.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User : liulu
 * Date : 2017/3/12 20:42
 * version $Id: GlobalParam.java, v 0.1 Exp $
 */
public class GlobalParam {

    private static final Map<String, Object> GLOBAL_PARAM = Collections.synchronizedMap(new HashMap<String, Object>(50));

    public static Object get(String key) {
        return GLOBAL_PARAM.get(key);
    }

    public static <T> T get(String key, Class<T> targetType) {
        //noinspection unchecked
        return (T) GLOBAL_PARAM.get(key);
    }

    public static Object put(String key, Object value) {
        return GLOBAL_PARAM.put(key, value);
    }

    public static Object remove(String key) {
        return GLOBAL_PARAM.remove(key);
    }


}
