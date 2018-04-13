package com.github.lit.param.model;

import com.github.lit.jdbc.annotation.Column;
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
 * Date : 17-9-17 下午2:39
 * version $Id: Param.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
@Table(name = PluginConst.TABLE_PREFIX + "param")
public class Param implements Serializable {

    private static final long serialVersionUID = -958178685990081472L;

    /**
     * 参数 Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paramId;

    /**
     * 参数编码
     */
    private String code;

    /**
     * 参数值
     */
    private String value;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否系统级
     */
    @Column(name = "is_system")
    private Boolean system;

}
