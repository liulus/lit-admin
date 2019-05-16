package com.github.lit.plugin.core.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-16
 */
@Data
@ConfigurationProperties(prefix = "lit.admin.configure")
public class PluginProperties {

    private Boolean singlePage = Boolean.TRUE;

    private String theme = "cyan";

}
