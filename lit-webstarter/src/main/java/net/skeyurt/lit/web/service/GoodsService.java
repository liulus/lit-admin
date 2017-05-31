package net.skeyurt.lit.web.service;

import net.skeyurt.lit.commons.exception.AppUnCheckedException;
import net.skeyurt.lit.dao.JdbcDao;
import net.skeyurt.lit.dao.builder.SqlSelect;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.transfer.QueryTransfer;
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
        return jdbcDao.queryPageList(Goods.class, vo, new QueryTransfer<GoodsVo>() {
            @Override
            public void transQuery(GoodsVo vo, SqlSelect select, Class<?> entityClass) {
                select.and("price", Operator.GT, vo.getStartPrice());
                select.and("price", Operator.LT, vo.getEndPrice());
            }
        });
    }


    public void testEx() {

//        throw new AppCheckedException("进错方法了");

        throw new AppUnCheckedException();
//        int i = 5 / 0;


    }
}
