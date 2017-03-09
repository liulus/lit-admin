package com.lit.test.start;

import com.lit.dao.JdbcDao;
import com.lit.test.base.BaseTest;
import com.lit.test.bean.Goods;
import com.lit.test.bean.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * User : liulu
 * Date : 2017-3-6 21:35
 * version $Id: JdbcDaoImplTest.java, v 0.1 Exp $
 */
@Slf4j
public class JdbcDaoImplTest extends BaseTest {

    @Resource
    private JdbcDao jdbcDao;

    private Goods goods1 = Goods.builder().code("11111111").name("第一个").inventory(10).price(9.98D).createTime(new Date()).delete(false).build();
    private Goods goods2 = Goods.builder().code("22222222").name("第二个").inventory(20).price(29.98D).createTime(new Date()).delete(false).build();

    private GoodsVo goodsVo = GoodsVo.builder().code("694800").delete(false).build();

    @Test
    public void insert() throws Exception {

        long start = System.currentTimeMillis();
        long id1 = jdbcDao.insert(goods1);
        printUseTime(start);
        log.info("\n id1 : {} \n", id1);

        start = System.currentTimeMillis();
        long id2 = jdbcDao.insert(goods2);
        printUseTime(start);
        log.info("\n id2 : {} \n", id2);

        int rows = jdbcDao.deleteByIds(Goods.class, id1, id2);
        log.info("\n 删除了 {} 行数据 \n", rows);

    }

    @Test
    public void delete() throws Exception {
        long id1 = jdbcDao.insert(goods1);
        goods1.setGoodsId(id1);

        long start = System.currentTimeMillis();
        int rows = jdbcDao.delete(goods1);
        printUseTime(start);
        log.info("\n 删除了 {} 行数据 \n", rows);
    }

    @Test
    public void update() throws Exception {
        long id1 = jdbcDao.insert(goods1);
        goods1.setGoodsId(id1);

        Goods goodsId1 = jdbcDao.get(Goods.class, id1);
        log.info("\n 修改前: {} \n", goodsId1);

        goodsId1.setName("名称修改");
        goodsId1.setPrice(18.88D);
        goodsId1.setInventory(null);

        int rows = jdbcDao.update(goodsId1);
        log.info("\n 修改了 {} 行数据 \n", rows);

        goodsId1 = jdbcDao.get(Goods.class, id1);
        log.info("\n 修改后: {} \n", goodsId1);

        jdbcDao.delete(goodsId1);


    }

    @Test
    public void update1() throws Exception {
        long id1 = jdbcDao.insert(goods1);
        goods1.setGoodsId(id1);

        Goods goodsId1 = jdbcDao.get(Goods.class, id1);
        log.info("\n 修改前: {} \n", goodsId1);

        goodsId1.setName("名称修改");
        goodsId1.setPrice(18.88D);
        goodsId1.setInventory(null);

        int rows = jdbcDao.update(goodsId1, false);
        log.info("\n 修改了 {} 行数据 \n", rows);

        goodsId1 = jdbcDao.get(Goods.class, id1);
        log.info("\n 修改后: {} \n", goodsId1);

        jdbcDao.delete(goodsId1);
    }

    @Test
    public void get() throws Exception {

    }

    @Test
    public void queryForSingle() throws Exception {
        Goods goods = jdbcDao.queryForSingle(Goods.class, goodsVo);
        log.info("\n {}",goods.toString());
    }

    @Test
    public void queryForSingle1() throws Exception {

    }

    @Test
    public void queryForSingle2() throws Exception {

    }

    @Test
    public void queryForSingle3() throws Exception {

    }

    @Test
    public void query() throws Exception {

    }

    @Test
    public void query1() throws Exception {

    }

    @Test
    public void query2() throws Exception {

    }

    @Test
    public void query3() throws Exception {

    }

    @Test
    public void queryPageList() throws Exception {

    }

    @Test
    public void queryPageList1() throws Exception {

    }

    @Test
    public void query4() throws Exception {

    }

    @Test
    public void count() throws Exception {

    }

    @Test
    public void count1() throws Exception {

    }

    @Test
    public void count2() throws Exception {

    }

    @Test
    public void count3() throws Exception {

    }

}