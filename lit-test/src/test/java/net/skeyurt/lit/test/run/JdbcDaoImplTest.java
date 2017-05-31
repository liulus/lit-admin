package net.skeyurt.lit.test.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.skeyurt.lit.commons.page.PageInfo;
import net.skeyurt.lit.commons.page.PageList;
import net.skeyurt.lit.commons.page.PageService;
import net.skeyurt.lit.dao.JdbcDao;
import net.skeyurt.lit.dao.builder.SqlSelect;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.transfer.QueryTransfer;
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
    private JdbcDao jdbcDao;

    private Goods goods1 = Goods.builder().code("11111111").name("第一个").inventory(10).price(9.98D).gmtCreate(new Date()).delete(false).build();
    private Goods goods2 = Goods.builder().code("22222222").name("第二个").inventory(20).price(29.98D).gmtCreate(new Date()).delete(false).build();

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

        log.info("\n 修改前: {} \n", goods1);

        goods1.setName("名称修改");
        goods1.setPrice(18.88D);
        goods1.setInventory(null);

        int rows = jdbcDao.update(goods1);
        log.info("\n 修改了 {} 行数据 \n", rows);

        log.info("\n 修改后: {} \n", goods1);

        jdbcDao.delete(goods1);


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
        Goods goods = jdbcDao.get(Goods.class, 10002);
        log.info("查询实体： {}", goods);
    }

    @Test
    public void queryForSingle() throws Exception {
        Goods goods = jdbcDao.queryForSingle(Goods.class, goodsVo);
        log.info("\n {}",goods.toString());
    }

    @Test
    public void queryForSingle1() throws Exception {
        Goods goods = jdbcDao.findByProperty(Goods.class, "code", "694800");
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

        PageService.setPager(20, 2);
        PageList<Goods> goodsList = (PageList<Goods>) jdbcDao.query(Goods.class, goodsVo, new QueryTransfer<GoodsVo>() {
            @Override
            public void transQuery(GoodsVo goodsVo, SqlSelect select, Class<?> entityClass) {
                select.or("price", Operator.GT, goodsVo.getStartPrice()).or("price", Operator.LT, goodsVo.getEndPrice());
            }
        });

        PageInfo pageInfo = goodsList.getPageInfo();

        log.info("pageSize: {}, pageNum: {}, totalRecord: {}", pageInfo.getPageSize(), pageInfo.getPageNum(), pageInfo.getTotalRecord());

        log.info("  \n{} \n {}", Arrays.toString(goodsList.toArray(new Goods[goodsList.size()])), goodsList.size());

        ObjectMapper objectMapper = new ObjectMapper();
        Writer writer = new StringWriter();
        objectMapper.writeValue(writer, goodsList);
        log.info("\n {}", writer.toString());
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