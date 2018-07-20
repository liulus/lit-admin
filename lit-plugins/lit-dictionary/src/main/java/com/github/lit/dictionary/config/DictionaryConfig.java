package com.github.lit.dictionary.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * User : liulu
 * Date : 2018/4/1 15:33
 * version $Id: DictionaryConfig.java, v 0.1 Exp $
 */
@Configuration
public class DictionaryConfig {

    @Resource
    private freemarker.template.Configuration configuration;

    @PostConstruct
    public void setSharedVariable() {
        this.configuration.setSharedVariable("dictTools", new DictionaryDirectiveModel());
    }


}
