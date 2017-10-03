package net.skeyurt.lit.commons.context;

import net.skeyurt.lit.commons.condition.ConditionalOnClass;
import net.skeyurt.lit.commons.mvc.ModelResultHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * User : liulu
 * Date : 17-10-3 下午1:54
 * version $Id: SpringMvcConfig.java, v 0.1 Exp $
 */
@Configuration
@ConditionalOnClass(DispatcherServlet.class)
public class SpringMvcConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new ModelResultHandlerInterceptor()).addPathPatterns("/**");
    }
}
