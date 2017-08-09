package net.skeyurt.lit.commons.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User : liulu
 * Date : 2017/8/9 21:52
 * version $Id: PublishEvent.java, v 0.1 Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PublishEvent {

    Class<?> eventClass();

    PublishType publishType() default PublishType.ASYNC;

    enum PublishType {
        SYNC,
        ASYNC,;
    }
}
