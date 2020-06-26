package com.github.lit.repository.entity;

import com.github.lit.constant.PluginConst;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
     * 类型
     */
    private String function;

    /**
     * 备注
     */
    private String remark;

}
