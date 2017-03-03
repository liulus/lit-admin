package com.lit.test.run;

import com.lit.dao.JdbcDao;
import com.lit.dao.builder.Criteria;
import com.lit.test.base.DBUtils;
import com.lit.test.entity.Goods;
import com.lit.test.qo.GoodsQo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * User : liulu
 * Date : 2017-1-1 23:49
 * version $Id: CriteriaTest.java, v 0.1 Exp $
 */
public class CriteriaTest {

    @Test
    public void insert() throws Exception {
//        Goods goods1 = new Goods("410245", "蒙牛果粒多", 3.5D, false, 10, new Date());
//        Goods goods2 = new Goods("803124", "哇哈哈矿泉水", 2D, false, 120, new Date());
//        JdbcDao jdbcDao = DBUtils.getJdbcDao();
//        long start = System.currentTimeMillis();
//        jdbcDao.insert(goods1);
//        System.out.println("=======用时=======》   " + (System.currentTimeMillis() - start));
//
//        start = System.currentTimeMillis();
//        jdbcDao.insert(goods2);
//        System.out.println("=======用时=======》   " + (System.currentTimeMillis() - start));
    }

    @Test
    public void query1() throws Exception {
        JdbcDao jdbcDao = DBUtils.getJdbcDao();
        Criteria criteria = Criteria.select(Goods.class).where("code", "803124");
        long start = System.currentTimeMillis();
        Goods goods = jdbcDao.queryForSingle(criteria);
        System.out.println("=======用时=======》   " + (System.currentTimeMillis() - start));
        System.out.println(goods);
        start = System.currentTimeMillis();
        goods = jdbcDao.queryForSingle(criteria);
        System.out.println("=======用时=======》   " + (System.currentTimeMillis() - start));
        System.out.println(goods);
    }

    @Test
    public void testPage () throws Exception{
        JdbcDao jdbcDao = DBUtils.getJdbcDao();
        GoodsQo qo = new GoodsQo();
        qo.setCode("535749");
        qo.setAaa("aaa");
        long start = System.currentTimeMillis();
//        List<Goods> goodses = jdbcDao.queryForList(Goods.class, qo);
//        PageResult<Goods> pageResult = jdbcDao.queryForPage(Goods.class, qo);
        System.out.println("\n用时 ：" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
//        List<Goods> goodses = jdbcDao.queryForList(Goods.class, qo);
//        PageResult<Goods> pageResult2 = jdbcDao.queryForPage(Goods.class, qo);
        System.out.println("\n用时 ：" + (System.currentTimeMillis() - start));

    }

    @Test
    public void testField () throws Exception {
        System.out.println(Goods.class.getDeclaredField("code") == null);
    }



    @Test
    public void init() throws Exception {
        JdbcDao jdbcDao = DBUtils.getJdbcDao();
        String goodsInfos = FileUtils.readFileToString(new File(getClass().getResource("/goods.txt").getPath()), String.valueOf(StandardCharsets.UTF_8));
        String[] goodsInfoArr = StringUtils.split(goodsInfos, "\r\n");
        int i = 1;
        long startFirst = System.currentTimeMillis();
        long start = System.currentTimeMillis();
        for (String goodsInfo : goodsInfoArr) {
            String[] goodsArr = StringUtils.split(goodsInfo, "<>");
            Goods goods = new Goods(goodsArr[0], goodsArr[1], Double.valueOf(goodsArr[2]));
            goods.setInventory(new BigDecimal(goodsArr[3]).intValue());
            goods.setIsDelete(false);
            jdbcDao.insert(goods);
            if (i++ % 1000 == 0) {
                System.out.println("1000用时 ：" + (System.currentTimeMillis() - start));
                start = System.currentTimeMillis();
            }
        }
        System.out.println("\n\n ====用时 ：" + (System.currentTimeMillis() - startFirst));
    }


}
