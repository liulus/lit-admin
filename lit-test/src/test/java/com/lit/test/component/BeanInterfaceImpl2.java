package com.lit.test.component;

import org.springframework.stereotype.Component;

/**
 * User : liulu
 * Date : 2017-2-21 17:58
 * version $Id: BeanInterfaceImpl2.java, v 0.1 Exp $
 */
@Component
public class BeanInterfaceImpl2 implements BeanInterface {

    public BeanInterfaceImpl2() {
        System.out.println("init2...");
    }

    @Override
    public void method1() {
        System.out.println("BeanInterfaceImpl2");
    }
}
