package com.lit.test.entity;

import java.util.Date;

/**
 * User : liulu
 * Date : 2017-2-17 20:10
 * version $Id: BooleanEntity.java, v 0.1 Exp $
 */
public class BooleanEntity {

    private Integer aaa;

    private Date date;

    private boolean isLock;

    private boolean isPrivate;

    public boolean getLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Integer getAaa() {
        return aaa;
    }

    public void setAaa(Integer aaa) {
        this.aaa = aaa;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
