package com.lit.test.qo.transfer;

import com.lit.dao.builder.Criteria;
import com.lit.dao.transfer.CriteriaTransfer;
import com.lit.test.qo.GoodsQo;
import org.apache.commons.lang3.StringUtils;

/**
 * User : liulu
 * Date : 2017-1-28 20:35
 * version $Id: GoodsQoTransfer.java, v 0.1 Exp $
 */
public class GoodsQoTransfer implements CriteriaTransfer<GoodsQo> {

    @Override
    public void transQuery(GoodsQo goodsQo, Criteria criteria, Class<?> entityClass) {

        if (StringUtils.isNotBlank(goodsQo.getCode())) {
            criteria.and("code", goodsQo.getCode());
        }
        if (StringUtils.isNotBlank(goodsQo.getName())) {
            criteria.and("name", "like", "%"+goodsQo.getName()+"%");
        }
        if (goodsQo.getCodes() != null && goodsQo.getCodes().size() != 0) {
            criteria.and("code", "in", goodsQo.getCodes().toArray());
        }

        criteria.desc("createTime", "goodsId");
    }
}
