package net.skeyurt.lit.test.run;

import net.skeyurt.lit.dictionary.entity.Dictionary;
import net.skeyurt.lit.dictionary.service.DictionaryService;
import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.jdbc.enums.Logic;
import net.skeyurt.lit.test.base.BaseTest;
import net.skeyurt.lit.test.bean.Goods;
import net.skeyurt.lit.test.bean.GoodsVo;
import net.skeyurt.lit.test.bean.Supplier;
import net.skeyurt.lit.test.config.TestConstants;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/3/10 22:40
 * version $Id: InitData.java, v 0.1 Exp $
 */
public class InitData extends BaseTest {

    @Resource
    private JdbcTools jdbcTools;

    @Resource
    private DictionaryService dictionaryService;


    @Test
    public void test() {
        System.out.println("sss");
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void initData() throws Exception {

        Dictionary goodsCategory = dictionaryService.findByRootKey(TestConstants.GOODS_CATEGORY);
        if (goodsCategory == null) {
            goodsCategory = new Dictionary();
            goodsCategory.setDictKey(TestConstants.GOODS_CATEGORY);
            goodsCategory.setDictValue("商品类别字典");
            dictionaryService.insert(goodsCategory);
        }

        InputStream inputStream = getClass().getResourceAsStream("/goods.txt");
        String text = IOUtils.toString(inputStream, "UTF-8");
        String lineFeed = text.contains("\n") ? "\n" : "\r\n";
        StringBuilder supplierCodeStr = new StringBuilder();
        long start = System.currentTimeMillis();
        int i = 1;
        for (String goodsInfoStr : text.split(lineFeed)) {
            String[] goodsInfo = goodsInfoStr.split("<>");
            String supplierCode = goodsInfo[10];
            String categoryCode = goodsInfo[8];
            Goods goods = Goods.builder().code(goodsInfo[0])
                    .barCode(goodsInfo[1])
                    .name(goodsInfo[2])
                    .specification(goodsInfo[3])
                    .unit(goodsInfo[4])
                    .inventory(Double.valueOf(goodsInfo[5]).intValue())
                    .price(Double.valueOf(goodsInfo[6]))
                    .purchaserCode(goodsInfo[7])
                    .category(categoryCode)
                    .supplierCode(supplierCode)
                    .build();
            jdbcTools.insert(goods);

            if (supplierCodeStr.indexOf(categoryCode) == -1) {
                supplierCodeStr.append(categoryCode).append(",");
                Dictionary child = new Dictionary();
                child.setDictKey(categoryCode);
                child.setDictValue(goodsInfo[9]);
                child.setParentId(goodsCategory.getDictId());
                dictionaryService.insert(child);
            }

            if (supplierCodeStr.indexOf(supplierCode + ",") == -1) {
                supplierCodeStr.append(supplierCode).append(",");
                Supplier supplier = Supplier.builder().code(supplierCode)
                        .name(goodsInfo[11])
                        .address("杭州市滨江区" + i + "号")
                        .contact("张三" + i)
                        .mobile(String.valueOf(13516784215L + i++ * 1254))
                        .build();
                jdbcTools.insert(supplier);
            }
        }
        printUseTime(start);
    }

    @Test
    public void testJoin() {

        List<GoodsVo> goodsVos = jdbcTools.createSelect(Goods.class)
                .simpleJoin(Supplier.class)
                .addField(Supplier.class, "name", "address")
                .alias("supplierName", "supplier_Addr")
                .joinCondition(Goods.class, "supplierCode", Logic.EQ, Supplier.class, "code")
                .page(1, 20)
                .list(GoodsVo.class);
        System.out.println(goodsVos);

    }
}
