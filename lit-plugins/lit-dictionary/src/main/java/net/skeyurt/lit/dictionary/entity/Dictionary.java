package net.skeyurt.lit.dictionary.entity;

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
 * Date : 2017/4/8 19:55
 * version $Id: Dictionary.java, v 0.1 Exp $
 */
@Getter
@Setter
@Table(name = PluginConst.TABLE_PREFIX + "dictionary")
public class Dictionary implements Serializable {

    private static final long serialVersionUID = -8733548251351142793L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dictId;

    /**
     * 字典 key
     */
    private String dictKey;

    /**
     * 字典 值
     */
    private String dictValue;

    /**
     * 顺序号
     */
    private Integer orderNum;

    /**
     * 字典层级
     */
    private Integer dictLevel;

    /**
     * 备注
     */
    private String memo;

    /**
     * 是否系统级别字典 （系统级不允许修改和删除）
     */
    @Column(name = "is_system")
    private Boolean system;

    /**
     * 父字典 Id
     */
    private Long parentId;

}
