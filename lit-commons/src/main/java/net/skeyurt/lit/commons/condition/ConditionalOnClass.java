package net.skeyurt.lit.commons.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * User : liulu
 * Date : 2017/3/31 21:47
 * version $Id: ConditionalOnClass.java, v 0.1 Exp $
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnClassCondition.class)
public @interface ConditionalOnClass {

    Class<?>[] value() default {};

    String[] name() default {};
}
