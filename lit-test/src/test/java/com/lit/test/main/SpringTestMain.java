package com.lit.test.main;

import com.lit.test.component.BeanInterface;
import com.lit.test.config.SpringConfig;
import com.lit.commons.utils.ApplicationContextUtils;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * User : liulu
 * Date : 2017-2-21 17:59
 * version $Id: SpringTestMain.java, v 0.1 Exp $
 */
public class SpringTestMain {

    @Test
    public void test1() throws Exception {
        new AnnotationConfigApplicationContext(SpringConfig.class);

        Map<String, BeanInterface> beanInterfaceMap = ApplicationContextUtils.getBeansOfType(BeanInterface.class);


        for (BeanInterface beanInterface : beanInterfaceMap.values()) {
            beanInterface.method1();
        }



    }



}
