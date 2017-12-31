package com.github.lit.test.run;

import com.github.lit.jdbc.JdbcTools;
import com.github.lit.jdbc.enums.Logic;
import com.github.lit.jdbc.statement.Select;
import com.github.lit.test.base.BaseTest;
import com.github.lit.test.bean.Goods;
import com.github.lit.test.bean.GoodsVo;
import com.github.lit.test.bean.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/6/22 20:18
 * version $Id: JdbcToolsTest.java, v 0.1 Exp $
 */
@Slf4j
@Transactional
public class JdbcToolsTest extends BaseTest {

    @Resource
    private JdbcTools jdbcTools;

//    private Goods goods = Goods.builder().code("80145236").name("娃哈哈哈哈哈哈哈!")
//            .barCode("6921168509256").specification("220g").unit("瓶").category("100606")
//            .price(2.0D).purchaserCode("33040590").supplierCode("00776").inventory(450).gmtCreate(new Date()).build();


    @Test
    public void testInsert1() {
        Goods goods = new Goods();
        goods.setCode("80145124");
        goods.setName("这是一个商品");
        // ...
        Long id = (Long) jdbcTools.insert(goods);
        log.info("插入的实体ID为: {}", id);
    }

    @Test
    public void testInsert2() {
        Long id = (Long) jdbcTools.createInsert(Goods.class)
                .into("code", "80145412")
                .into("name", "农夫山泉")
                .into("price", 2D)
                .execute();
        log.info("插入的实体ID为: {}", id);
    }

    @Test
    public void testUpdate1() {
        // update 操作是根据实体的 id 来更新记录, 所以要保证实体中的 Id一定不为空

        Goods goods = new Goods();
        goods.setGoodsId(12301L);
        goods.setCode("8501221");
        goods.setName("康师傅绿茶");

        //实体中值为 null 的属性`不会`更新到数据库中
        jdbcTools.update(goods);

        // 实体中值为 null 的属性也会更新到数据库中，
        jdbcTools.update(goods, false);
    }

    @Test
    public void testUpdate2() {

        // 会将 code 为 80145412 的商品的 name 和 price 更新, 返回受影响记录的条数

        int rows = jdbcTools.createUpdate(Goods.class)
                .set("name", "统一红茶")
                .set("price", 3.0D)
                .where("code", "80145412")
                .execute();
    }

    @Test
    public void testDelete() {

        Goods goods = new Goods();
        goods.setGoodsId(1200L);

        // 方式一, 根据实体中的 Id 值删除记录, 返回受影响记录的条数
        int rows = jdbcTools.delete(goods);

        // 方式二, 根据一批 id 值 删除指定的实体记录, 返回受影响记录的条数
        rows = jdbcTools.deleteByIds(Goods.class, 1200L, 1201L);

        // 方式三 删除 code 为 80145412 的商品, 返回受影响记录的条数
        rows = jdbcTools.createDelete(Goods.class)
                .where("code", "80145412")
                .execute();
    }

    @Test
    public void testSelect1() {
        Goods goods = jdbcTools.get(Goods.class, 1203L);
    }

    @Test
    public void testSelect2() {
        Select<Goods> select = jdbcTools.createSelect(Goods.class)
                .where("code", "8014512") // 添加条件 code=? 参数 8014512
                .and("price", Logic.GTEQ, 20.98D) // 添加条件 and price >= ? 参数 20.98
                .or("inventory", Logic.LT, 120); // 添加条件 or inventory < ? 参数 120


        // 返回 count
        int count = select.count();

        // 返回 单个 实体对象, 如果查询结果有多条, 会抛出异常, 查询结果为空, 返回 null
        Goods goods = select.single();

        // 返回实体列表
        List<Goods> list = select.list();

        // 返回分页查询结果列表, 可以强转 为 PageList, 获取分页信息
        List<Goods> pageList1 = select.page(1, 20).list();

        // 返回分页查询结果列表, 但是 不会查询 count, 只查询分页结果
        List<Goods> pageList2 = select.page(1, 20, false).list();
    }

    @Test
    public void testSelect3() {
        // 指定白名单, 将只查询 白名单里的字段
        List<Goods> goodsList1 = jdbcTools.createSelect(Goods.class)
                .include("code", "name") // 只查询 code 和 name
                .where("name", Logic.LIKE, "%娃哈哈%") //  条件 name like ? 参数 %娃哈哈%
                .and().parenthesis().condition("price", Logic.LTEQ, 38.7D) // 添加带有括号的 and条件   and ( price <= ? 参数 38.7
                .or("supplierCode", "00776") // 条件 supplier_code=? 参数 00776
                .end() // 结束刚才 and 条件添加的括号  )
                .list(); // 查询列表


        // 指定黑名单, 除了黑名单里的字段, 其他全部查询
        List<Goods> goodsList2 = jdbcTools.createSelect(Goods.class)
                .exclude("code", "name") // 查询将排除 code 和 name
                .list(); // 查询列表
    }

    @Test
    public void testSelect4() {

        // 1. 指定函数名, 默认全部字段, 结果会添加  count(*)
        Integer count = jdbcTools.createSelect(Goods.class)
                .addFunc("count")
                .single(Integer.class); // 指定函数返回类型

        // 2. 指定函数名和字段, 结果会添加  max(price)
        jdbcTools.createSelect(Goods.class)
                .addFunc("max", "price")
                .single(Double.class); // 指定返回类型

        // 3. 指定函数名和字段, 第二个参数是 isDistinct, 结果会添加 count( distinct supplier_code )
        jdbcTools.createSelect(Goods.class)
                .addFunc("count", true, "supplierCode")
                .single(Integer.class);
    }


    @Test
    public void testSelect5() {

        // 为函数指定别名 和 查询结果的 返回类型
        // max(price) 的查询结果将会绑定到 GoodsVo 的 maxPrice 属性上
        // count(inventory) 的查询结果将会绑定到 GoodsVo 的 inventoryCount 属性上
        jdbcTools.createSelect(Goods.class)
                .addFunc("max", "price")
                .addFunc("count", "inventory")
                .alias("maxPrice", "inventoryCount")
                .single(GoodsVo.class); // 指定返回类型

        // 另一种写法
        jdbcTools.createSelect(Goods.class)
                .addFunc("max", "price").alias("maxPrice")
                .addFunc("count", "inventory").alias("inventoryCount")
                .single(GoodsVo.class);
    }


    @Test
    public void testSelect6() {

        // 注: asc 和 desc 方法 可以放多个字段
        List<Goods> list = jdbcTools.createSelect(Goods.class)
                .where("name", Logic.LIKE, "%娃哈哈%")
                .asc("code") // 按 code 升序排列
                .desc("gmtCreate") // 按创建时间降序排列
                .list();
    }

    @Test
    public void testSelect7() {

        // 按供应商代码 supplierCode 查询 最大价格和最大库存
        jdbcTools.createSelect(Goods.class)
                .include("supplierCode")
                .addFunc("max", "price")
                .addFunc("max", "inventory")
                .alias("maxPrice", "maxInventory")
                .where("price", Logic.GTEQ, 19.98)
                .and("inventory", Logic.GTEQ, 10)
                .groupBy("supplierCode")
                // 添加 having 条件
                .having("maxPrice", Logic.GTEQ, 198)
                .and("maxInventory", Logic.GTEQ, 100)
                .list(GoodsVo.class);
    }

    @Test
    public void testSelect8() {

        jdbcTools.createSelect(Goods.class)
                // join 语句, 和 on 一起使用 设置关联条件
                .join(Supplier.class)
                .on(Supplier.class, "code", Logic.EQ, Goods.class, "supplierCode")

                // 添加 供应商表中的字段
                .addField(Supplier.class, "name")
                .addField(Supplier.class, "address")

                // 设置别名, 与 GoodsVo 中属性名一致,
                .alias("supplierName", "supplierAddr")
                .where("price", Logic.GTEQ, 19.98)
                .list(GoodsVo.class);
    }

    @Test
    public void testSelect9() {

        jdbcTools.createSelect(Goods.class)
                // 使用简单join  需要和 joinCondition 一起使用设置关联条件
                .simpleJoin(Supplier.class)

                // 添加 供应商表中的字段
                .addField(Supplier.class, "name")
                .addField(Supplier.class, "address")

                // 设置别名, 与 GoodsVo 中属性名一致,
                .alias("supplierName", "supplierAddr")
                .joinCondition(Supplier.class, "code", Logic.EQ, Goods.class, "supplierCode")
                .and("price", Logic.GTEQ, 19.98)
                .list(GoodsVo.class);
    }

}
