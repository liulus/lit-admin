package com.github.lit.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-03
 */
@Configuration
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {


    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        web.ignoring().antMatchers("/**/*.css", "/**/*.js", "/libs/**", "/images/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("marissa").password("koala").roles("USER")
                .and()
                .withUser("paul").password("emu").roles("USER")
                .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //        http.authorizeRequests().antMatchers("/libs/**").permitAll();

//        http.authorizeRequests()
//                .antMatchers("/oauth/**").permitAll();

        // @formatter:off
//        http
//                .requestMatchers().anyRequest()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/oauth/*").permitAll()
        ;
        // @formatter:on

//                http.formLogin().loginPage("/user/login").permitAll()
//                        .usernameParameter("userName").passwordParameter("password")
//                        .successHandler(new SessionAuthenticationSuccessHandler())
//                        .and().csrf().disable()
//                        .httpBasic().disable()
//                        .authorizeRequests().anyRequest().authenticated();
    }

}
