package com.github.lit.config;

import com.github.lit.context.LoginUserService;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-10
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class Oauth2WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService loginUserService() {
        return new LoginUserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginUserService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/**/*.css", "/**/*.js", "/images/**", "/libs/**")
                .antMatchers("/error/**")
//                .antMatchers("/index")
                .antMatchers("/user")
//                .antMatchers("/login", "/register", "/forget")
                .antMatchers("/api/user/register")
        ;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JwtLoginHandler handler = new JwtLoginHandler();
        // 对于前台页面请求, 禁用一些不必要的拦截器
        http
                .formLogin()
                .loginPage("/login")
                .successHandler(handler)
                .failureHandler(handler)
                .and()
                .addFilterBefore(new JwtAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout().disable()
                .securityContext().disable()
                .headers().disable()
                .sessionManagement().disable()
                .anonymous().disable()
                .requestCache().disable()
                .csrf().disable()
                .antMatcher("/api/**")
                .authorizeRequests()
                .anyRequest().authenticated()
        ;

    }


}
