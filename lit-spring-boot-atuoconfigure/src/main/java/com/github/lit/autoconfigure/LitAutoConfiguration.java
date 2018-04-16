package com.github.lit.autoconfigure;

import com.github.lit.web.annotation.EnableLitWeb;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * User : liulu
 * Date : 2017/12/29 15:03
 * version $Id: LitAutoConfiguration.java, v 0.1 Exp $
 */
@Configuration
@EnableLitWeb
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 5)
@EnableConfigurationProperties(LitProperties.class)
public class LitAutoConfiguration {





}
