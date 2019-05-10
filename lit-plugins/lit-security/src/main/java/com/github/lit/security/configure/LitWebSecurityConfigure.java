package com.github.lit.security.configure;

import com.github.lit.security.config.SessionAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-03
 */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class LitWebSecurityConfigure {

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
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            public void configure(WebSecurity web) throws Exception {
                web.ignoring().antMatchers(HttpMethod.OPTIONS)
                        .antMatchers("/**/*.css", "/**/*.js", "/images/**")
                ;
            }

            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.formLogin().loginPage("/user/login").permitAll()
                        .usernameParameter("userName").passwordParameter("password")
                        .successHandler(new SessionAuthenticationSuccessHandler())
                        .and().csrf().disable()
                        .httpBasic().disable()
                        .authorizeRequests().anyRequest().authenticated();
            }
        };
    }



}
