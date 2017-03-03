package com.lit.test.bean;

import com.lit.test.entity.Goods;

import java.util.Date;

/**
 * User : liulu
 * Date : 2017-2-19 20:52
 * version $Id: TargetBean.java, v 0.1 Exp $
 */
public class TargetBean {

    private Goods goods;

    private Integer aaa;
    private long bbb;
    private double ccc;
    private Date ddd;
    private String eee;

    public TargetBean() {
    }

    public TargetBean(Goods goods, Integer aaa, long bbb, double ccc, Date ddd, String eee) {
        this.goods = goods;
        this.aaa = aaa;
        this.bbb = bbb;
        this.ccc = ccc;
        this.ddd = ddd;
        this.eee = eee;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getAaa() {
        return aaa;
    }

    public void setAaa(Integer aaa) {
        this.aaa = aaa;
    }

    public long getBbb() {
        return bbb;
    }

    public void setBbb(long bbb) {
        this.bbb = bbb;
    }

    public double getCcc() {
        return ccc;
    }

    public void setCcc(double ccc) {
        this.ccc = ccc;
    }

    public Date getDdd() {
        return ddd;
    }

    public void setDdd(Date ddd) {
        this.ddd = ddd;
    }

    public String getEee() {
        return eee;
    }

    public void setEee(String eee) {
        this.eee = eee;
    }

    @Override
    public String toString() {
        return "TargetBean{" +
                "goods=" + goods +
                ", aaa=" + aaa +
                ", bbb=" + bbb +
                ", ccc=" + ccc +
                ", ddd=" + ddd +
                ", eee='" + eee + '\'' +
                '}';
    }
}
