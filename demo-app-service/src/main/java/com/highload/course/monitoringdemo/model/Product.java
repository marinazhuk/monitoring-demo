package com.highload.course.monitoringdemo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "catalog")
public class Product {

    @Id
    @Field(type = FieldType.Text)
    private String id;
    @Field(type = FieldType.Text)
    private String productCode;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Text)
    private String category;

    public Product() {
    }

    public Product(String productCode, String name, String category) {
        this.productCode = productCode;
        this.name = name;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productCode='" + productCode + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    public String getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
