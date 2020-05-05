package com.lit.service.security.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * User : liulu
 * Date : 2017/10/24 09:37
 * version $Id: SecurityResourceLoader.java, v 0.1 Exp $
 */
@Slf4j
public class SecurityResourceLoader {

    private static List<String> IGNORING_URL;

    private static Map<String, String[]> AUTHENTICATE_URL;


    public static List<String> getIgnoringUrl() {
        if (IGNORING_URL == null) {
            synchronized (SecurityResourceLoader.class) {
                loadIgnoreUrl();
            }
            IGNORING_URL.addAll(Arrays.asList("/libs/**", "/js/**", "/css/**", "/images/**"));
        }
        return IGNORING_URL;
    }

    public static Map<String, String[]> getAuthenticateUrl() {
        if (AUTHENTICATE_URL == null) {
            synchronized (SecurityResourceLoader.class) {
                loadAuthenticateUrl();
            }
        }
        return AUTHENTICATE_URL;
    }


    /**
     * 加载白名单
     */
    private static void loadIgnoreUrl() {
        if (IGNORING_URL != null) {
            return;
        }
        IGNORING_URL = new ArrayList<>();

        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = patternResolver.getResources("classpath*:/security/ignoring-url.txt");
            for (Resource resource : resources) {
//                List<String> lines = Resources.readLines(resource.getURL(), Charset.defaultCharset());
//                IGNORING_URL.addAll(lines);
            }
        } catch (IOException e) {
            log.error("load ignoring url error ", e);
        }
    }

    /**
     * 加载需要权限的 url
     */
    private static void loadAuthenticateUrl() {
        if (AUTHENTICATE_URL != null) {
            return;
        }
        AUTHENTICATE_URL = new HashMap<>();

        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = patternResolver.getResources("classpath*:/security/security.properties");
            Properties properties = new Properties();
            for (Resource resource : resources) {
                properties.load(resource.getInputStream());
            }

            for (Map.Entry<Object, Object> entry : properties.entrySet()) {

                String key = (String) entry.getKey();
                String value = (String) entry.getValue();

                AUTHENTICATE_URL.put(key, value.split(","));
            }

        } catch (IOException e) {
            log.error("load authenticate url error ", e);
        }


    }


}
