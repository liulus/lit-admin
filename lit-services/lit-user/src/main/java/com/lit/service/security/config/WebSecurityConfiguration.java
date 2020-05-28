package com.lit.service.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author liulu
 * @version 1.0
 * created_at 2020/5/28
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/**/*.css", "/**/*.js", "/images/**", "/libs/**")
                .antMatchers("/error/**")
                .antMatchers("/user/login")
                .antMatchers("/login", "/register", "/forget")
                .antMatchers("/api/user/register")
        ;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 对于前台页面请求, 禁用一些不必要的拦截器
        http.formLogin().disable()
                .logout().disable()
                .securityContext().disable()
                .headers().disable()
                .sessionManagement().disable()
                .anonymous().disable()
                .requestCache().disable()
                .csrf().disable()
//                .addFilterAfter(HeaderWriterFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated()
        ;

    }
}
