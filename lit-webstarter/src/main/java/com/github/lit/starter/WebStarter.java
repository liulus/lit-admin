package com.github.lit.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * User : liulu
 * Date : 2017/3/19 14:17
 * version $Id: WebStarter.java, v 0.1 Exp $
 */
@SpringBootApplication
@ComponentScan("com.github.lit")
public class WebStarter extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebStarter.class);
    }


    /**
     * 覆盖此方法 是为了打成的 war包能直接放到web容器中运行
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebStarter.class);
    }
}
