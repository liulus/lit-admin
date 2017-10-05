package net.skeyurt.lit.user.config;

import net.skeyurt.lit.user.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * User : liulu
 * Date : 17-10-5 上午10:06
 * version $Id: UserConfig.java, v 0.1 Exp $
 */
@Configuration
public class UserConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**");
    }
}
