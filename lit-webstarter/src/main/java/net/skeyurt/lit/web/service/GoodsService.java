package net.skeyurt.lit.web.service;

import com.github.lit.plugin.exception.AppException;
import org.springframework.stereotype.Service;

//import net.skeyurt.lit.web.entity.Goods;

/**
 * User : liulu
 * Date : 2017/3/19 16:10
 * version $Id: GoodsService.java, v 0.1 Exp $
 */
@Service
public class GoodsService {

//    @Resource
//    private JdbcTools jdbcTools;

//    public List<Goods> queryPageList(GoodsVo vo) {
//        vo.setStartPrice(19.98D);
//        vo.setEndPrice(99.98D);
////        return jdbcTools.query(Goods.class, vo);
//        return null;
//    }


    public void testEx() {

        throw new AppException("进错方法了");

//        throw new AppUnCheckedException();
//        int i = 5 / 0;


    }
}
