package com.github.lit.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * User : liulu
 * Date : 2018/4/14 22:13
 * version $Id: WebSecurityConfig.java, v 0.1 Exp $
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 将权限认证的 role_ 前缀去除
     *
     * @return MethodSecurityInterceptorProcessor
     */
    @Bean
    public MethodSecurityInterceptorProcessor methodSecurityInterceptorProcessor() {
        return new MethodSecurityInterceptorProcessor();
    }



//    @Bean
    public ResourceServerConfigurer resourceServerConfigurer() {
        return new ResourceServerConfigurerAdapter(){

            @Override
            public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                http.httpBasic().disable();
            }
        };
    }



}
