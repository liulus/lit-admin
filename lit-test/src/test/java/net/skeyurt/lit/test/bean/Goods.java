package net.skeyurt.lit.test.bean;

import net.skeyurt.lit.dao.annotation.Column;
import net.skeyurt.lit.dao.annotation.GeneratedValue;
import net.skeyurt.lit.dao.annotation.Table;
import net.skeyurt.lit.dao.enums.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * User : liulu
 * Date : 2017-1-9 21:05
 * version $Id: Goods.java, v 0.1 Exp $
 */
@Table(name = "lit_goods")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goods implements Serializable {

    private static final long serialVersionUID = -7915472099544797458L;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long goodsId;

    private String code;

    private String name;

    private Double price;

    @Column(name = "is_delete")
    private Boolean delete;

    private Integer inventory;

    private Date gmtCreate;

    private String aaa1;private String aaa6;private String aaa11;private String aaa16;private String aaa21;private String aaa26;
    private String aaa2;private String aaa7;private String aaa12;private String aaa17;private String aaa22;private String aaa27;
    private String aaa3;private String aaa8;private String aaa13;private String aaa18;private String aaa23;private String aaa28;
    private String aaa4;private String aaa9;private String aaa14;private String aaa19;private String aaa24;private String aaa29;
    private String aaa5;private String aaa10;private String aaa15;private String aaa20;private String aaa25;private String aaa30;



}
