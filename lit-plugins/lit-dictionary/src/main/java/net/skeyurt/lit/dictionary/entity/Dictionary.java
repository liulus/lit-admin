package net.skeyurt.lit.dictionary.entity;

import lombok.Data;
import net.skeyurt.lit.dao.annotation.*;
import net.skeyurt.lit.dao.enums.GenerationType;
import net.skeyurt.lit.dictionary.qo.qct.DictionaryTransfer;

import java.io.Serializable;

/**
 * User : liulu
 * Date : 2017/4/8 19:55
 * version $Id: Dictionary.java, v 0.1 Exp $
 */
@Data
@Table(name = "lit_dictionary")
@TransferClass(DictionaryTransfer.class)
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
