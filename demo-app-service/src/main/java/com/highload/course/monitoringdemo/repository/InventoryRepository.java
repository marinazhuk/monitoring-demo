package com.highload.course.monitoringdemo.repository;

import com.highload.course.monitoringdemo.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<Inventory, String> {

    Inventory findByProductCode(String productCode);
}
