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
 * Date : 2017/11/19 16:28
 * version $Id: UserRole.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
@Table(name = PluginConst.TABLE_PREFIX + "user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -8814219062642647147L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 角色编号
     */
    private String roleCode;

}
