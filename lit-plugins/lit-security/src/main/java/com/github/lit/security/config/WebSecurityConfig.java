package com.github.lit.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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


    @Bean
    public WebSecurityConfigurerAdapter customerWebSecurityConfigurer(UserDetailsService userDetailsService) {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
            }

            @Override
            public void configure(WebSecurity web) throws Exception {
                web.ignoring().antMatchers("/**/*.css", "/**/*.js", "/libs/**", "/images/**");
            }

            @Override
            protected void configure(HttpSecurity http) throws Exception {
                //        http.authorizeRequests().antMatchers("/libs/**").permitAll();

                http.formLogin().loginPage("/user/login").usernameParameter("userName").passwordParameter("password")
                        .defaultSuccessUrl("/user/pass", true).permitAll()
                        .and().csrf().disable()
                        .authorizeRequests().anyRequest().authenticated();
            }
        };
    }

}
