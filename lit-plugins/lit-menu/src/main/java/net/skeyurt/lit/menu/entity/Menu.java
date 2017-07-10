package net.skeyurt.lit.menu.entity;

import lombok.Getter;
import lombok.Setter;
import net.skeyurt.lit.jdbc.annotation.Column;
import net.skeyurt.lit.jdbc.annotation.GeneratedValue;
import net.skeyurt.lit.jdbc.annotation.Id;
import net.skeyurt.lit.jdbc.annotation.Table;
import net.skeyurt.lit.jdbc.enums.GenerationType;
import net.skeyurt.lit.plugin.context.PluginConst;

import java.io.Serializable;

/**
 * User : liulu
 * Date : 2017/7/10 10:06
 * version $Id: Menu.java, v 0.1 Exp $
 */
@Getter
@Setter
@Table(name = PluginConst.TABLE_PREFIX + "menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = -6077674673399919945L;

    /**
     * 菜单Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单url
     */
    private String menuUrl;

    /**
     * 顺序号
     */
    private Integer orderNum;

    /**
     * 备注
     */
    private String memo;

    /**
     * 菜单类型
     */
    private String menuType;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 是否启用
     */
    @Column(name = "is_enable")
    private Boolean enable;

    /**
     * 父菜单Id
     */
    private Long parentId;


}
