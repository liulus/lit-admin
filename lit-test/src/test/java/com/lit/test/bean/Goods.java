package com.lit.test.bean;

import com.lit.dao.annotation.Column;
import com.lit.dao.annotation.GeneratedValue;
import com.lit.dao.annotation.Table;
import com.lit.dao.enums.GenerationType;
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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goodsId;

    private String code;

    private String name;

    private Double price;

    @Column(name = "is_delete")
    private Boolean delete;

    private Integer inventory;

    private Date createTime;
}
