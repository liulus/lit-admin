package com.lit.test.entity;

import javax.persistence.Table;

import java.util.Date;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2017-1-9 21:05
 * version $Id: Goods.java, v 0.1 Exp $
 */
@Table(name = "lit_goods")
public class Goods {

    private Long goodsId;

    private String code;

    private String name;

    private Double price;

    private Boolean isDelete;

    private Integer inventory;

    private Date createTime;

    public Goods() {
    }

    public Goods(String code) {
        this.code = code;
    }

    public Goods(String code, String name, Double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public Goods(String code, String name, Double price, Boolean isDelete) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.isDelete = isDelete;
    }

    public Goods(String code, String name, Double price, Boolean isDelete, Integer inventory) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.isDelete = isDelete;
        this.inventory = inventory;
    }

    public Goods(String code, String name, Double price, Boolean isDelete, Integer inventory, Date createTime) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.isDelete = isDelete;
        this.inventory = inventory;
        this.createTime = createTime;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean delete) {
        isDelete = delete;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Goods)) return false;
        Goods goods = (Goods) o;
        return Objects.equals(goodsId, goods.goodsId) &&
                Objects.equals(code, goods.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goodsId, code);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", isDelete=" + isDelete +
                ", inventory=" + inventory +
                ", createTime=" + createTime +
                '}';
    }
}
