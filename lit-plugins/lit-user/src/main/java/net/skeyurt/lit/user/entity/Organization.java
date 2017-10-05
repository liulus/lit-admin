package net.skeyurt.lit.user.entity;

import lombok.Getter;
import lombok.Setter;
import net.skeyurt.lit.jdbc.annotation.GeneratedValue;
import net.skeyurt.lit.jdbc.annotation.Id;
import net.skeyurt.lit.jdbc.annotation.Table;
import net.skeyurt.lit.jdbc.enums.GenerationType;
import net.skeyurt.lit.plugin.context.PluginConst;

import java.io.Serializable;

/**
 * User : liulu
 * Date : 17-10-3 下午4:22
 * version $Id: Organization.java, v 0.1 Exp $
 */
@Getter
@Setter
@Table(name = PluginConst.TABLE_PREFIX + "organization")
public class Organization implements Serializable{

    private static final long serialVersionUID = -713093592928469785L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orgId;

    /** 机构号 */
    private String orgCode;

    /** 机构名 */
    private String orgName;

    /** 机构简称 */
    private String shortName;

    /** 机构类型 */
    private String orgType;

    /** 机构层级 */
    private Integer orgLevel;

    /** 特殊序号, 用于查询 */
    private String serialNum;

    /** 地址 */
    private String orgAddress;

}
