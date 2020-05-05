package com.lit.service.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * User : liulu
 * Date : 2018/4/15 21:34
 * version $Id: LoginUser.java, v 0.1 Exp $
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 6675934947596333693L;

    private Long id;

    /**
     * 用户编码
     */
    private String code;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别 true: 男, false: 女
     */
    private Boolean sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobileNum;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户状态
     */
    private String userStatus;

    /**
     * 是否锁定
     */
    private Boolean lock;

    /**** Organization ****/

    private Long orgId;

    private String orgCode;

    private String orgName;

    private String levelIndex;


    /*********** 权限信息 ************/
    List<String> auths;


    public boolean hasOrg() {
        return orgId != null;
    }

}
