package com.github.lit.user.model;

import com.github.lit.jdbc.annotation.Column;
import com.github.lit.jdbc.annotation.GeneratedValue;
import com.github.lit.jdbc.annotation.Id;
import com.github.lit.jdbc.annotation.Table;
import com.github.lit.jdbc.enums.GenerationType;
import com.github.lit.plugin.core.constant.PluginConst;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * User : liulu
 * Date : 2017/8/12 10:51
 * version $Id: User.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
@Table(name = PluginConst.TABLE_PREFIX + "user")
public class User implements Serializable {

    private static final long serialVersionUID = -4209108788354210143L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orgId;

    /** 用户编码 */
    private String code;

    /** 工号 */
    private String jobNum;

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
    private Boolean gender;

    /** 邮箱 */
    private String email;

    /** 手机号 */
    private String mobileNum;

    /** 电话 */
    private String telephone;

    /** 身份证号 */
    private String idCardNum;

    /** 用户类型 */
    private String type;

    /** 用户状态 */
    private String status;

    /** 是否锁定 */
    @Column(name = "is_lock")
    private Boolean lock;

    /** 创建人 */
    private String creator;

    /** 创建时间 */
    private Date gmtCreate;



}
