package com.github.lit.dictionary.model;

import com.github.lit.jdbc.annotation.Column;
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
 * Date : 2017/4/8 19:55
 * version $Id: Dictionary.java, v 0.1 Exp $
 */
@Getter
@Setter
@ToString
@Table(name = PluginConst.TABLE_PREFIX + "dictionary")
public class Dictionary implements Serializable {

    private static final long serialVersionUID = -8733548251351142793L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父字典 Id
     */
    private Long parentId;

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
     * 备注
     */
    private String remark;

    /**
     * 是否系统级别字典 （系统级不允许删除）
     */
    @Column(name = "is_system")
    private Boolean system;
}
