package com.github.lit.security.config;

import com.github.lit.security.context.LitPasswordEncoder;
import com.github.lit.security.context.LitUserDetailService;
import com.github.lit.security.context.SecurityResourceLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

/**
 * User : liulu
 * Date : 2017/10/23 09:46
 * version $Id: SecurityConf.java, v 0.1 Exp $
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConf extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(WebSecurity web) throws Exception {
        List<String> ignoringUrl = SecurityResourceLoader.getIgnoringUrl();
        web.ignoring().antMatchers(ignoringUrl.toArray(new String[ignoringUrl.size()]));
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return new LitUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new LitPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        Map<String, String[]> authenticateUrl = SecurityResourceLoader.getAuthenticateUrl();

        for (Map.Entry<String, String[]> entry : authenticateUrl.entrySet()) {
            String key = entry.getKey();
            if (key.contains(",")) {
                StringBuilder access = new StringBuilder();
                for (String authCode : key.split(",")) {
                    access.append("hasRole('").append(authCode).append("') and");
                }
                http.authorizeRequests().antMatchers(entry.getValue()).access(access.substring(0, access.length() - 3));
            } else {
                http.authorizeRequests().antMatchers(entry.getValue()).hasRole(key);
            }
        }

        http.formLogin().loginPage("/plugin/user/login").usernameParameter("userName")
                .passwordParameter("password").permitAll().successForwardUrl("/plugin/user/login").and()
                .csrf().disable()
                .authorizeRequests().anyRequest().authenticated();

    }

}
