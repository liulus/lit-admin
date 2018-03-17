package com.github.lit.test.run;

import com.github.lit.dictionary.model.Dictionary;
import com.github.lit.dictionary.service.DictionaryService;
import com.github.lit.jdbc.JdbcTools;
import com.github.lit.test.base.BaseTest;
import com.github.lit.test.bean.District;
import com.github.lit.test.bean.Goods;
import com.github.lit.test.bean.Supplier;
import com.github.lit.test.config.TestConstants;
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


    // province, city，district
    @Test
    @Transactional
    @Rollback(value = false)
    public void testDis() throws Exception {

        InputStream inputStream = getClass().getResourceAsStream("/dis.txt");

        List<String> strings = IOUtils.readLines(inputStream, "UTF-8");

        long provinceId = 0, cityId = 0;

        int pCount = 1;
        int cCount = 1;
        int dCount = 1;
        for (String line : strings) {
            String[] split = line.split("##");
            String code = split[0].trim();
            String name = split[1].trim();

            int type = getType(code);

            District district = new District();
            district.setCode(code);
            district.setName(name);

            if (type >= 4) {
                district.setOrderNum(pCount++);
                district.setType("province");
                jdbcTools.insert(district);
                provinceId = district.getId();
                cCount = 1;
            } else if (type >= 2) {
                district.setOrderNum(cCount++);
                district.setType("city");
                district.setParentId(provinceId);
                jdbcTools.insert(district);
                cityId = district.getId();
                dCount = 1;
            } else if (type >= 0) {
                if (name.equals("市辖区")) {
                    continue;
                }
                district.setOrderNum(dCount++);
                district.setType("district");
                district.setParentId(cityId);
                jdbcTools.insert(district);
            }

        }



    }

    private int getType(String code) {

        StringBuilder sb = new StringBuilder(code).reverse();

        int count = 0;
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) != '0') {
                break;
            }
            count ++;
        }
        return count;
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

}
