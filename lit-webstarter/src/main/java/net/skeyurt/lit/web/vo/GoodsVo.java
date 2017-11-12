package net.skeyurt.lit.web.vo;

import com.github.lit.commons.page.Page;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * User : liulu
 * Date : 2017-3-6 22:15
 * version $Id: GoodsVo.java, v 0.1 Exp $
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo extends Page implements Serializable {

    private static final long serialVersionUID = -6502691308871630951L;

    private Long goodsId;

    private String code;

    private String name;

    private Double price;

    private Boolean delete;

    private Integer inventory;

    private Date gmtCreate;

    private Double startPrice;

    private Double endPrice;




}
