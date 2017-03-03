package com.lit.test.bean;

import com.lit.test.entity.Goods;

import java.util.Date;

/**
 * User : liulu
 * Date : 2017-2-19 20:52
 * version $Id: SourceBean.java, v 0.1 Exp $
 */
public class SourceBean {

    private Goods goods;

    private int aaa;
    private long bbb;
    private double ccc;
    private Date ddd;
    private String eee;

    public SourceBean() {
    }

    public SourceBean(Goods goods, int aaa, long bbb, double ccc, Date ddd, String eee) {
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

    public int getAaa() {
        return aaa;
    }

    public void setAaa(int aaa) {
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
        return "SourceBean{" +
                "goods=" + goods +
                ", aaa=" + aaa +
                ", bbb=" + bbb +
                ", ccc=" + ccc +
                ", ddd=" + ddd +
                ", eee='" + eee + '\'' +
                '}';
    }
}
