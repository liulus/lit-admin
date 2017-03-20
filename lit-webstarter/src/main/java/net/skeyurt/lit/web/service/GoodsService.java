package net.skeyurt.lit.web.service;

import net.skeyurt.lit.dao.JdbcDao;
import net.skeyurt.lit.dao.builder.Criteria;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.transfer.CriteriaTransfer;
import net.skeyurt.lit.web.entity.Goods;
import net.skeyurt.lit.web.vo.GoodsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/3/19 16:10
 * version $Id: GoodsService.java, v 0.1 Exp $
 */
@Service
public class GoodsService {

    @Resource
    private JdbcDao jdbcDao;

    public List<Goods> queryPageList(GoodsVo vo) {
        vo.setStartPrice(19.98D);
        vo.setEndPrice(99.98D);
        return jdbcDao.queryPageList(Goods.class, vo, new CriteriaTransfer<GoodsVo>() {
            @Override
            public void transQuery(GoodsVo vo, Criteria criteria, Class<?> entityClass) {
                criteria.and("price", Operator.GT, vo.getStartPrice());
                criteria.and("price", Operator.LT, vo.getEndPrice());
            }
        });
    }


}
