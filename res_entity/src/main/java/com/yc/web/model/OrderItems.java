package com.yc.web.model;

import java.io.Serializable;

public class OrderItems implements Serializable {
    private String fname;
    private Double realprice;
    private Integer num;//这个商品的数量
    private Double smallCount;//小计

    public OrderItems() {
    }

    public OrderItems(String fname, Double realprice, Integer num) {
        this.fname = fname;
        this.realprice = realprice;
        this.num = num;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Double getRealprice() {
        return realprice;
    }

    public void setRealprice(Double realprice) {
        this.realprice = realprice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getSmallCount() {
        return this.realprice* this.num;
    }

    public void setSmallCount(Double smallCount) {
        this.smallCount = smallCount;
    }

    @Override
    public String toString() {
        return "orderItem{" +
                "fname='" + fname + '\'' +
                ", realprice=" + realprice +
                ", num=" + num +
                ", smallCount=" + smallCount +
                '}';
    }
}
