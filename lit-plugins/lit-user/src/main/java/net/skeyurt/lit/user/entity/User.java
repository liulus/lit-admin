package net.skeyurt.lit.user.entity;

import lombok.Data;
import net.skeyurt.lit.jdbc.annotation.GeneratedValue;
import net.skeyurt.lit.jdbc.annotation.Id;
import net.skeyurt.lit.jdbc.annotation.Table;
import net.skeyurt.lit.jdbc.enums.GenerationType;
import net.skeyurt.lit.plugin.context.PluginConst;

import java.io.Serializable;
import java.util.Date;

/**
 * User : liulu
 * Date : 2017/8/12 10:51
 * version $Id: User.java, v 0.1 Exp $
 */
@Data
@Table(name = PluginConst.TABLE_PREFIX + "user")
public class User implements Serializable {

    private static final long serialVersionUID = -4209108788354210143L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /** 用户编码 */
    private String userCode;

    /** 用户名 */
    private String userName;

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

    /** 昵称 */
    private String nickName;

    /** 真实名称 */
    private String realName;

    /** 身份证号 */
    private String idCardNum;

    /** 用户类型 */
    private String userType;

    /** 用户状态 */
    private Integer userStatus;

    /** 是否锁定 */
    private Boolean lock;

    /** 是否启用 */
    private Boolean enable;

    /** 创建时间 */
    private Date createTime;


}
