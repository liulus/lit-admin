package com.github.lit.user.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * User : liulu
 * Date : 2017/8/12 11:42
 * version $Id: LoginUser.java, v 0.1 Exp $
 */
@Getter
@Setter
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 4254995122049457520L;

    private Long userId;

    /** 用户编码 */
    private String userCode;

    /** 用户名 */
    private String userName;

    /** 昵称 */
    private String nickName;

    /** 真实名称 */
    private String realName;

    /** 头像 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 性别 true: 男, false: 女 */
    private Boolean sex;

    /** 邮箱 */
    private String email;

    /** 手机号 */
    private String mobilePhone;

    /** 电话 */
    private String telephone;

    /** 身份证号 */
    private String idCardNum;

    /** 用户类型 */
    private String userType;

    /** 用户状态 */
    private String userStatus;

    /** 是否锁定 */
    private Boolean lock;

    /** 上次登录时间 */
    private Date gmtLastLogin;

    private Long orgId;

    private String orgCode;

    private String orgName;

    private String orgType;

    private Integer orgLevel;

    private String shortName;

    private String orgAddress;

    private String serialNum;

    public boolean hasOrg() {
        return this.orgCode != null && !this.orgCode.isEmpty();
    }

}
