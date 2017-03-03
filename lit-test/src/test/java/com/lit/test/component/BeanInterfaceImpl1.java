package com.lit.test.component;

import org.springframework.stereotype.Component;

/**
 * User : liulu
 * Date : 2017-2-21 17:57
 * version $Id: BeanInterfaceImpl1.java, v 0.1 Exp $
 */
@Component
public class BeanInterfaceImpl1 implements BeanInterface {

    public BeanInterfaceImpl1() {
        System.out.println("init1...");
    }

    @Override
    public void method1() {
        System.out.println("BeanInterfaceImpl1");
    }
}
