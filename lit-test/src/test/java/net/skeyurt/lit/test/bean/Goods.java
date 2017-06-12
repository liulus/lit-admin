package net.skeyurt.lit.test.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.skeyurt.lit.jdbc.annotation.GeneratedValue;
import net.skeyurt.lit.jdbc.annotation.Id;
import net.skeyurt.lit.jdbc.annotation.Table;
import net.skeyurt.lit.jdbc.enums.GenerationType;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, sequenceName = "seq_goods")
    private Long goodsId;

    private String code;

    private String barCode;

    private String specification;

    private String unit;

    private String category;

    private String purchaserCode;

    private String supplierCode;

    private String name;

    private Double price;

    private Integer inventory;

    private Date gmtCreate;


}
