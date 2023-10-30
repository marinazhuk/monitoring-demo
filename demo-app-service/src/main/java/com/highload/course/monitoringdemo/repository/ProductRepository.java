package com.highload.course.monitoringdemo.repository;

import com.highload.course.monitoringdemo.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    List<Product> findByCategoryContaining(String category);
}
