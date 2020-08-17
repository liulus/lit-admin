package com.github.lit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-13
 */
@Data
@ConfigurationProperties(prefix = "lit.admin.user")
public class UserProperties {


    private String loginPage = "default/login";

    private Boolean enableRegister = false;

    private String registerPage = "default/register";

    private String forgetPage = "default/forget";

}
