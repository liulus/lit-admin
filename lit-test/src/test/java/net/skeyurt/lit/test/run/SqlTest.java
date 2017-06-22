package net.skeyurt.lit.test.run;

import net.skeyurt.lit.jdbc.enums.Logic;
import net.skeyurt.lit.jdbc.spring.JdbcTemplateToolsImpl;
import net.skeyurt.lit.test.bean.Goods;
import net.skeyurt.lit.test.bean.GoodsVo;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * User : liulu
 * Date : 2017/6/4 18:28
 * version $Id: SqlTest.java, v 0.1 Exp $
 */
public class SqlTest {


    @Test
    public void test1() {

        JdbcTemplateToolsImpl jdbcTools = new JdbcTemplateToolsImpl(new JdbcTemplate());
        jdbcTools.setDbName("ORACLE");

//        Object execute = jdbcTools.createInsert(Goods.class).field("code", "price").values("822", 19.3D).execute();

        jdbcTools.createSelect(Goods.class).tableAlias("g")
                .join(GoodsVo.class).tableAlias("gv")
                .on(Goods.class, "code", Logic.EQ, GoodsVo.class, "code")
                .addFunc("max", "price")
                .alias("maxPrice")
                .joinCondition(Goods.class, "code", Logic.EQ, GoodsVo.class, "code")
                .groupBy("code")
                .having("maxPrice", Logic.GTEQ, 19.98D)
                .asc("goodsId")
                .single();


    }


}
