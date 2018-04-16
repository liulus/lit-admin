package com.github.lit.web.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lit.commons.spring.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

/**
 * User : liulu
 * Date : 2017/8/13 7:26
 * version $Id: PluginConfig.java, v 0.1 Exp $
 */
@Configuration
@ConditionalOnClass({ContentNegotiatingViewResolver.class, ObjectMapper.class})
public class PluginConfig {




}
