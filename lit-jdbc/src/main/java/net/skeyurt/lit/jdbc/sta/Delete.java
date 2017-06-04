package net.skeyurt.lit.jdbc.sta;

/**
 * User : liulu
 * Date : 2017/6/4 16:59
 * version $Id: Delete.java, v 0.1 Exp $
 */
public interface Delete extends Condition<Delete>{

    Delete initEntity(Object entity);


    int execute();
}
