package com.github.lit.menu.model;

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
 * Date : 2017/7/10 10:06
 * version $Id: Menu.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
@Table(name = PluginConst.TABLE_PREFIX + "menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = -6077674673399919945L;

    /**
     * 菜单Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父菜单Id
     */
    private Long parentId;

    /**
     * 菜单编码
     */
    private String code;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单url
     */
    private String url;

    /**
     * 顺序号
     */
    private Integer orderNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单类型
     */
    private String type;

    /**
     * 是否启用
     */
    @Column(name = "is_enable")
    private Boolean enable;

    /**
     * 权限码
     */
    private String authCode;

}
