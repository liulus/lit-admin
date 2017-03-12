package net.skeyurt.lit.test.run;

import net.skeyurt.lit.dao.JdbcDao;
import net.skeyurt.lit.test.base.BaseTest;
import net.skeyurt.lit.test.bean.Goods;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * User : liulu
 * Date : 2017/3/10 22:40
 * version $Id: InitData.java, v 0.1 Exp $
 */
public class InitData extends BaseTest {

    @Resource
    private JdbcDao jdbcDao;

    @Test
    @Transactional
    @Rollback(value = false)
    public void initData() throws Exception {

        InputStream inputStream = getClass().getResourceAsStream("/goods.txt");
        String text = IOUtils.toString(inputStream, "UTF-8");
        String lineFeed = text.contains("\n") ? "\n" : "\r\n";
        for (String goodsInfoStr : text.split(lineFeed)) {
            String[] goodsInfo = goodsInfoStr.split("<>");
            Goods goods = Goods.builder().code(goodsInfo[0]).name(goodsInfo[1]).price(Double.valueOf(goodsInfo[2]))
                    .inventory(Double.valueOf(goodsInfo[3]).intValue()).delete(false).build();
            jdbcDao.insert(goods);
        }
    }
}
