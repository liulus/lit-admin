package net.skeyurt.lit.param.entity;

import lombok.Getter;
import lombok.Setter;
import net.skeyurt.lit.jdbc.annotation.Column;
import net.skeyurt.lit.jdbc.annotation.GeneratedValue;
import net.skeyurt.lit.jdbc.annotation.Id;
import net.skeyurt.lit.jdbc.annotation.Table;
import net.skeyurt.lit.jdbc.enums.GenerationType;
import net.skeyurt.lit.plugin.context.PluginConst;

import java.io.Serializable;

/**
 * User : liulu
 * Date : 17-9-17 下午2:39
 * version $Id: Param.java, v 0.1 Exp $
 */
@Getter
@Setter
@Table(name = PluginConst.TABLE_PREFIX + "param")
public class Param implements Serializable {

    private static final long serialVersionUID = -958178685990081472L;

    /**
     * 参数 Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paramId;

    /**
     * 参数编码
     */
    private String paramCode;

    /**
     * 参数值
     */
    private String paramValue;


    /**
     * 备注
     */
    private String memo;

    /**
     * 是否系统级
     */
    @Column(name = "is_system")
    private Boolean system;

    /**
     * 是否启动加载
     */
    @Column(name = "is_load")
    private Boolean load;

}
