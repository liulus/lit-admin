package com.github.lit.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * User : liulu
 * Date : 2017/12/29 15:07
 * version $Id: LitProperties.java, v 0.1 Exp $
 */
@Getter
@Setter
@ConfigurationProperties(prefix = LitProperties.LIT_PREFIX)
public class LitProperties {

    static final String LIT_PREFIX = "lit";





}
