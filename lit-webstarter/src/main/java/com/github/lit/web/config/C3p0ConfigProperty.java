package com.github.lit.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * User : liulu
 * Date : 2017/3/19 14:41
 * version $Id: ConfigProperty.java, v 0.1 Exp $
 */
@Data
@Component
@ConfigurationProperties(prefix = "c3p0")
public class C3p0ConfigProperty {

    private String jdbcUrl;

    private String driverClass;

    private String user;

    private String password;

    /** 连接池中保留的最小连接数 */
    private int minPoolSize = 3;

    /** 连接池中保留的最大连接数 */
    private int maxPoolSize = 15;

    /** 初始化连接池中的连接数，取值应在poolMinSize与poolMaxSize之间，默认为3 */
    private int initialPoolSize = 3;

    /** 最大空闲时间，多少秒内未使用则连接被丢弃。若为0则永不丢弃。默认值: 0  */
    private int maxIdleTime = 0;

    /** 当连接池连接耗尽时，客户端调用getConnection()后等待获取新连接的时间，超时后将抛出SQLException，如设为0则无限期等待。单位毫秒。默认: 0 */
    private int checkTimeOut = 0;

    private boolean autoCommitOnClose = false;

    private String automaticTestTable;


}
