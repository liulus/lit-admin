package com.github.lit.user.entity;

import com.github.lit.jdbc.annotation.GeneratedValue;
import com.github.lit.jdbc.annotation.Id;
import com.github.lit.jdbc.annotation.Table;
import com.github.lit.jdbc.enums.GenerationType;
import com.github.lit.plugin.context.PluginConst;
import lombok.Getter;
import lombok.Setter;

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

    /** 父机构 Id */
    private Long parentId;

    /** 机构号 */
    private String orgCode;

    /** 机构名 */
    private String orgName;

    /** 机构简称 */
    private String shortName;

    /** 机构类型 */
    private String orgType;

    /** 机构层级 从 1 开始 */
    private Integer orgLevel;

    /**
     * 特殊序号, 用于查询
     * 一个层级 3 位数字, 从 001 开始, 子机构: 父机构 serialNum + 3位数字
     * 例: 010 -> 层级为 1 的第 10 个机构, 没有父机构
     * 例: 007003 -> 层级为 2 且父机构 serialNum 为 007 的第 3 个机构
     */
    private String serialNum;

    /** 地址 */
    private String orgAddress;

    /** 备注 */
    private String memo;

}
