package com.highload.course.monitoringdemo.model;

import org.springframework.data.annotation.Id;

public class Inventory {
    @Id
    private String id;
    private String productCode;
    private Integer quantity;

    public Inventory() {
    }

    public Inventory(String productCode, Integer quantity) {
        this.productCode = productCode;
        this.quantity = quantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
