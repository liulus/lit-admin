package com.github.lit.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.header.HeaderWriterFilter;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-10
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
//            return new InMemoryTokenStore();
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("lit-admin");
        return converter;
    }

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
                .addFilterAfter(new Oauth2TokenProcessingFilter(tokenStore()), HeaderWriterFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated()
        ;

    }
}
