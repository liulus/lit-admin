package net.skeyurt.lit.test.run;

import lombok.extern.slf4j.Slf4j;
import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.test.base.BaseTest;
import net.skeyurt.lit.test.bean.Goods;
import net.skeyurt.lit.test.bean.GoodsVo;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;

/**
 * User : liulu
 * Date : 2017-3-6 21:35
 * version $Id: JdbcDaoImplTest.java, v 0.1 Exp $
 */
@Slf4j
@Transactional
public class JdbcDaoImplTest extends BaseTest {

    @Resource
    private JdbcTools jdbcTools;

    private Goods goods1 = Goods.builder().code("11111111").name("第一个").inventory(10).price(9.98D).gmtCreate(new Date()).delete(false).build();
    private Goods goods2 = Goods.builder().code("22222222").name("第二个").inventory(20).price(29.98D).gmtCreate(new Date()).delete(false).build();

    private GoodsVo goodsVo = GoodsVo.builder().code("694800").delete(false).build();

    @Test
    public void insert() throws Exception {

        long start = System.currentTimeMillis();
        long id1 = (long) jdbcTools.insert(goods1);
        printUseTime(start);
        log.info("\n id1 : {} \n", id1);

        start = System.currentTimeMillis();
        long id2 = (long) jdbcTools.insert(goods2);
        printUseTime(start);
        log.info("\n id2 : {} \n", id2);

        int rows = jdbcTools.deleteByIds(Goods.class, id1, id2);
        log.info("\n 删除了 {} 行数据 \n", rows);

    }

    @Test
    public void delete() throws Exception {
        long id1 = (long) jdbcTools.insert(goods1);
        goods1.setGoodsId(id1);

        long start = System.currentTimeMillis();
        int rows = jdbcTools.delete(goods1);
        printUseTime(start);
        log.info("\n 删除了 {} 行数据 \n", rows);
    }

    @Test
    public void update() throws Exception {
        long id1 = (long) jdbcTools.insert(goods1);
        goods1.setGoodsId(id1);

        log.info("\n 修改前: {} \n", goods1);

        goods1.setName("名称修改");
        goods1.setPrice(18.88D);
        goods1.setInventory(null);

        int rows = jdbcTools.update(goods1);
        log.info("\n 修改了 {} 行数据 \n", rows);

        log.info("\n 修改后: {} \n", goods1);

        jdbcTools.delete(goods1);


    }

    @Test
    public void update1() throws Exception {
        long id1 = (long) jdbcTools.insert(goods1);
        goods1.setGoodsId(id1);

        Goods goodsId1 = jdbcTools.get(Goods.class, id1);
        log.info("\n 修改前: {} \n", goodsId1);

        goodsId1.setName("名称修改");
        goodsId1.setPrice(18.88D);
        goodsId1.setInventory(null);

        int rows = jdbcTools.update(goodsId1, false);
        log.info("\n 修改了 {} 行数据 \n", rows);

        goodsId1 = jdbcTools.get(Goods.class, id1);
        log.info("\n 修改后: {} \n", goodsId1);

        jdbcTools.delete(goodsId1);
    }

    @Test
    public void get() throws Exception {
        Goods goods = jdbcTools.get(Goods.class, 10002);
        log.info("查询实体： {}", goods);
    }

    @Test
    public void queryForSingle() throws Exception {
        Goods goods = jdbcTools.queryForSingle(Goods.class, goodsVo);
        log.info("\n {}",goods.toString());
    }

    @Test
    public void queryForSingle1() throws Exception {
        Goods goods = jdbcTools.findByProperty(Goods.class, "code", "694800");
        log.info("\n {}",goods.toString());
    }

    @Test
    public void queryForSingle2() throws Exception {

    }

    @Test
    public void queryForSingle3() throws Exception {

    }

    @Test
    public void query() throws Exception {
        GoodsVo goodsVo = GoodsVo.builder().startPrice(9.98D).endPrice(26D).build();

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