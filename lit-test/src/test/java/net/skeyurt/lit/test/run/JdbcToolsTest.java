package net.skeyurt.lit.test.run;

import lombok.extern.slf4j.Slf4j;
import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.jdbc.enums.Logic;
import net.skeyurt.lit.jdbc.sta.Select;
import net.skeyurt.lit.test.base.BaseTest;
import net.skeyurt.lit.test.bean.Goods;
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
        // ...
        Long id = (Long) jdbcTools.insert(goods);
        log.info("插入的实体ID为: {}", id);
    }

    @Test
    public void testInsert2() {
        Long id = (Long) jdbcTools.createInsert(Goods.class)
                .field("code", "name", "price")
                .values("80145412", "农夫山泉", 2D)
                .execute();
        log.info("插入的实体ID为: {}", id);
    }

    @Test
    public void testUpdate1() {
        // update 操作是根据实体的 id 来更新记录, 所以要保证实体中的 Id一定不为空

        Goods goods = new Goods();
        goods.setGoodsId(12301L);
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
                .set("name", "price")
                .values("统一红茶", 3.0D)
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
                .andWithBracket("price", Logic.LTEQ, 38.7D) // 添加带有括号的 and条件   and ( price <= ? 参数 38.7
                .or("supplierCode", "00776") // 条件 supplier_code=? 参数 00776
                .end() // 结束刚才 and 条件添加的括号  )
                .list(); // 查询列表


        // 指定黑名单, 除了黑名单里的字段, 其他全部查询
        List<Goods> goodsList2 = jdbcTools.createSelect(Goods.class)
                .exclude("code", "name") // 查询将排除 code 和 name
                .list(); // 查询列表
    }





}
