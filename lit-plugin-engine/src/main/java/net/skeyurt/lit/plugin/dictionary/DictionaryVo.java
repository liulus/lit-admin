package net.skeyurt.lit.plugin.dictionary;

/**
 * User : liulu
 * Date : 2017/7/14 11:13
 * version $Id: DictionaryVo.java, v 0.1 Exp $
 */
public class DictionaryVo {


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
    private Boolean system;

    /**
     * 父字典 Id
     */
    private Long parentId;


}
