package com.github.lit.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * User : liulu
 * Date : 17-10-3 下午3:51
 * version $Id: UserVo.java, v 0.1 Exp $
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {

    private static final long serialVersionUID = -7054655578166527259L;

    private Long userId;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实名称
     */
    private String realName;

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
    private Boolean gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobilePhone;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 身份证号
     */
    private String idCardNum;

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

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 上次登录时间
     */
    private Date gmtLastLogin;

    private Long orgId;

    private String orgCode;

    private String orgName;

    private String serialNum;


}
