package net.skeyurt.lit.dao.annotation;

import net.skeyurt.lit.dao.transfer.QueryTransfer;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User : liulu
 * Date : 2017/4/8 21:32
 * version $Id: TransferClass.java, v 0.1 Exp $
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface TransferClass {

    Class<? extends QueryTransfer> value();
}
