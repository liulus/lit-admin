package com.lit.test.run;

import com.lit.dao.JdbcDao;
import com.lit.dao.builder.Criteria;
import com.lit.test.base.DBUtils;
import com.lit.test.entity.Goods;
import com.lit.test.qo.GoodsQo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.*;

/**
 * User : liulu
 * Date : 2017-1-12 21:04
 * version $Id: JdbcDaoTest.java, v 0.1 Exp $
 */
public class JdbcDaoTest {

    private JdbcDao jdbcDao;

    private JdbcOperations jdbcTemplate;

    private void printTime(long start) {
        System.out.println("\n=======用时=======》   " + (System.currentTimeMillis() - start));
    }

    @Before
    public void before() throws Exception {
        jdbcDao = DBUtils.getJdbcDao();
        jdbcTemplate = DBUtils.getJdbcOperations();
        String databaseName = DBUtils.getDataSource().getConnection().getMetaData().getDatabaseProductName();
        System.out.println("init " + databaseName + "  datasource  completed !!!");
    }

    @Test
    public void insertEntity() throws Exception {
        Goods goods1 = new Goods("11111111", "蒙牛果粒多", 3.5D, false, 10, new Date());
        Goods goods2 = new Goods("22222222", "哇哈哈矿泉水", 2D, false, 120, new Date());
        long start = System.currentTimeMillis();
        jdbcDao.insert(goods1);
        printTime(start);

        start = System.currentTimeMillis();
        jdbcDao.insert(goods2);
        printTime(start);
    }

    @Test
    public void insertCriteria() throws Exception {
        Criteria criteria1 = Criteria.insert(Goods.class).into("code", "33333333").into("name", "姚生记开心果")
                .into("price", 18.6D).into("isDelete", false).into("inventory", 46).into("createTime", new Date());
        Criteria criteria2 = Criteria.insert(Goods.class).into("code", "44444444").into("name", "乐事薯片")
                .into("price", 18.6D).into("isDelete", false).into("inventory", 46).into("createTime", new Date());
        long start = System.currentTimeMillis();
        jdbcDao.insert(criteria1);
        printTime(start);

        start = System.currentTimeMillis();
        jdbcDao.insert(criteria2);
        printTime(start);
    }

    @Test
    public void updateEntity() throws Exception {
        String sql = "select goods_id from lit_goods where code = ?";
        Long goodsId1 = jdbcTemplate.queryForObject(sql, Long.class, "11111111");
        Long goodsId2 = jdbcTemplate.queryForObject(sql, Long.class,  "22222222");
        Goods goods1 = new Goods();
        goods1.setGoodsId(goodsId1);
        goods1.setName("蒙牛果粒多更新了==");
        Goods goods2 = new Goods();
        goods2.setGoodsId(goodsId2);
        goods2.setName("哇哈哈矿泉水更新了==");
        long start = System.currentTimeMillis();
        jdbcDao.update(goods1);
        printTime(start);
        start = System.currentTimeMillis();
        jdbcDao.update(goods2);
        printTime(start);
        System.out.println(jdbcDao.queryForSingle(Criteria.select(Goods.class).and("code", "11111111")));;
        System.out.println(jdbcDao.queryForSingle(Criteria.select(Goods.class).where("code", "22222222")));

    }

    @Test
    public void updateCriteria() throws Exception {
        Criteria criteria1 = Criteria.update(Goods.class).set("name", "更新名称1==").and("code", "33333333");
        Criteria criteria2 = Criteria.update(Goods.class).set("name", "更新名称2==").where("code", "44444444");
        long start = System.currentTimeMillis();
        jdbcDao.update(criteria1);
        printTime(start);
        start = System.currentTimeMillis();
        jdbcDao.update(criteria2);
        printTime(start);
        System.out.println(jdbcDao.queryForSingle(Criteria.select(Goods.class).and("code", "33333333")));;
        System.out.println(jdbcDao.queryForSingle(Criteria.select(Goods.class).where("code", "44444444")));

    }

    @Test
    public void deleteEntity() throws Exception {
        String sql = "select goods_id from lit_goods where code = ?";
        Long goodsId1 = jdbcTemplate.queryForObject(sql, Long.class, "11111111");
        Long goodsId2 = jdbcTemplate.queryForObject(sql, Long.class,  "22222222");
        Goods goods1 = new Goods();
        goods1.setGoodsId(goodsId1);
        Goods goods2 = new Goods();
        goods2.setGoodsId(goodsId2);
        long start = System.currentTimeMillis();
        jdbcDao.delete(goods1);
        printTime(start);

        start = System.currentTimeMillis();
        jdbcDao.delete(goods2);
        printTime(start);
    }

    @Test
    public void deleteCriteria() throws Exception {
        Criteria criteria1 = Criteria.delete(Goods.class).where("code", "33333333");
        Criteria criteria2 = Criteria.delete(Goods.class).where("code", "44444444");
        long start = System.currentTimeMillis();
        jdbcDao.delete(criteria1);
        printTime(start);

        start = System.currentTimeMillis();
        jdbcDao.delete(criteria2);
        printTime(start);
    }

    @Test
    public void deleteByIds() throws Exception {
        jdbcDao.deleteByIds(Goods.class, 14392, 14393, 14394, 14395);
    }

    @Test
    public void get() throws Exception {
        Goods goods = jdbcDao.get(Goods.class, 2222);
        System.out.println(goods);
    }

    @Test
    public void queryForSingle() throws Exception {
        GoodsQo qo1 = new GoodsQo();
        qo1.setCode("513047");
        qo1.setAaa("aaa");
        Goods goods = jdbcDao.queryForSingle(Goods.class, qo1);
        System.out.println(goods);

        GoodsQo qo2 = new GoodsQo();
        qo2.setName("妙脆角法式香乳烤肉味土豆角");
        System.out.println(jdbcDao.queryForSingle(Goods.class, qo2));
    }

    @Test
    public void queryForSingle1() throws Exception {
        Criteria criteria1 = Criteria.select(Goods.class).where("code", "513047");
        System.out.println(jdbcDao.queryForSingle(criteria1));
        Criteria criteria2 = Criteria.select(Goods.class).where("name", "like", "妙脆角法式香乳烤肉味土豆角");
        System.out.println(jdbcDao.queryForSingle(criteria2));
    }

    @Test
    public void queryForSingle2() throws Exception {
        String sql = "select code, create_time, goods_id, price, is_delete, name, inventory from lit_goods where code = ?";
        Goods goods = jdbcDao.queryForSingle(Goods.class, sql, "513047");
        System.out.println(goods);
    }

    @Test
    public void queryForSingle3() throws Exception {
        String sql = "select code, create_time, goods_id, price, is_delete, name, inventory from lit_goods where code = ?";
        Map<String, Object> map = jdbcDao.queryForSingle(sql, "513047");
        System.out.println(map);
    }

    @Test
    public void query() throws Exception {
        GoodsQo qo = new GoodsQo();
        qo.setCodes(new ArrayList<>(Arrays.asList("513047", "799016", "176515", "629833")));
        List<Goods> goodses = jdbcDao.query(Goods.class, qo);
        System.out.println(goodses);
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
    public void queryForPage() throws Exception {
        GoodsQo qo = new GoodsQo();
        qo.setName("水");
//        PageResult<Goods> pageResult = jdbcDao.queryForPage(Goods.class, qo, false);
//        System.out.println(pageResult.getData());
//        System.out.println(pageResult.getPageCount());
    }

    @Test
    public void queryForPage1() throws Exception {

    }

    @Test
    public void queryForPage2() throws Exception {

    }

    @Test
    public void queryForPage3() throws Exception {

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
    public void queryForObject() throws Exception {

    }

    @Test
    public void getJdbcTemplate() throws Exception {

    }

}