package com.github.lit.security.model;

import com.github.lit.jdbc.annotation.GeneratedValue;
import com.github.lit.jdbc.annotation.Id;
import com.github.lit.jdbc.annotation.Table;
import com.github.lit.jdbc.enums.GenerationType;
import com.github.lit.plugin.context.PluginConst;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * User : liulu
 * Date : 2017/11/19 16:20
 * version $Id: Authority.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
@Table(name = PluginConst.TABLE_PREFIX + "authority")
public class Authority implements Serializable {

    private static final long serialVersionUID = 1650537028788209650L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 权限码
     */
    private String code;

    /**
     * 权限码名称
     */
    private String name;

    /**
     * 权限类型
     */
    private String module;

    /**
     * 备注
     */
    private String remark;

}
