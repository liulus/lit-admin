package com.lit.test.run;

import com.lit.commons.bean.BeanUtils;
import com.lit.commons.bean.ConvertCallBack;
import com.lit.test.bean.SourceBean;
import com.lit.test.bean.TargetBean;
import com.lit.test.entity.BooleanEntity;
import com.lit.test.entity.Goods;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Date;

/**
 * User : liulu
 * Date : 2017-2-17 20:11
 * version $Id: CommonTest.java, v 0.1 Exp $
 */
public class CommonTest {

    @Test
    public void test1() throws Exception {

        BeanInfo beanInfo = Introspector.getBeanInfo(BooleanEntity.class);

        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            System.out.println(int.class.isPrimitive() +"----"+ Integer.class.isPrimitive());


            System.out.println(pd.getName() + " ----- " + pd.getPropertyType().getName());
        }

    }

    @Test
    public void test2() throws Exception {
        Goods goods = new Goods("1111", "2222", 9.98d, false, 34, new Date());
        SourceBean source = new SourceBean(goods, 111, 222L, 333d, new Date(), "444");
        TargetBean targetBean = BeanUtils.convert(new TargetBean(), source,new String[]{"aaa"}, new ConvertCallBack<TargetBean, SourceBean>() {
            @Override
            public void convertCallBack(TargetBean target, SourceBean source) {
                System.out.println("callback before : " + target.toString());

                target.setAaa(44444444);
                target.setDdd(new Date());
                target.setEee("sssss");

                System.out.println("callback after : " + target.toString());
            }
        });
    }


}
