package com.lit.service.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-16
 */
@Data
@ConfigurationProperties(prefix = "lit.core")
public class CoreProperties {

    private Boolean singlePage = Boolean.FALSE;
    private String theme = "cyan";
    private String indexPath = "/";

}
