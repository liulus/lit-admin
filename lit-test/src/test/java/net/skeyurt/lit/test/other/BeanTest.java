package net.skeyurt.lit.test.other;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * User : liulu
 * Date : 2017-1-7 12:30
 * version $Id: BeanTest.java, v 0.1 Exp $
 */
public class BeanTest {

    /**
     * 测试父类和子类初始化顺序
     * 类加载时会执行类的静态方法块，且只执行一次， 加载子类会先加载父类
     * 创建子类对象初始化顺序 ：父类非静态快 -> 父类构造方法 -> 子类非静态快 -> 子类构造方法
     */
    @Test
    public void test1() throws Exception {

        ClassLoader classLoader = Child.class.getClassLoader();
        System.out.println(classLoader);
        printSplitLine();
//        Class.forName(Child.class.getName());
        Class.forName(Parent.class.getName());
        printSplitLine();
        Child child1 = new Child();
        printSplitLine();
        Child child2 = new Child();
    }


    /**
     * getDeclaredFields 和 getFields 的区别：
     * getFields ： 获取类所有的 public 字段， 包括父类
     * getDeclaredFields ：获取本类所有的字段，但不包括父类字段，public的也不包括
     * 同样的还有 getMethods, getDeclaredMethods; getConstructors, getDeclaredConstructors
     *
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        Field[] fields = Child.class.getFields();
        System.out.println(Arrays.toString(fields));
        printSplitLine();
        Field[] declaredFields = Child.class.getDeclaredFields();
        System.out.println(Arrays.toString(declaredFields));

    }


    private void printSplitLine() {
        System.out.println("\n=======================分割线=======================\n");
    }

}
