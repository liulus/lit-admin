package com.github.lit.security.entity;

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
 * Date : 2017/11/19 16:24
 * version $Id: Role.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
@Table(name = PluginConst.TABLE_PREFIX + "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 5145432426032902715L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    /**
     * 角色编号
     */
    private String roleCode;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色备注
     */
    private String memo;


}
