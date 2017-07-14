package net.skeyurt.lit.menu.vo;

import lombok.Getter;
import lombok.Setter;
import net.skeyurt.lit.commons.page.Pager;

/**
 * User : liulu
 * Date : 2017/7/13 19:49
 * version $Id: MenuVo.java, v 0.1 Exp $
 */
@Getter
@Setter
public class MenuVo extends Pager {

    private static final long serialVersionUID = -4104369004390601578L;


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
    private Boolean enable;

    /**
     * 父菜单Id
     */
    private Long parentId;
}
