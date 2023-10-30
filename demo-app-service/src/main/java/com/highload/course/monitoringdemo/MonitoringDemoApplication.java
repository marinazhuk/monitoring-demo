package com.highload.course.monitoringdemo;

import com.highload.course.monitoringdemo.model.Inventory;
import com.highload.course.monitoringdemo.model.Product;
import com.highload.course.monitoringdemo.repository.InventoryRepository;
import com.highload.course.monitoringdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableElasticsearchRepositories
@EnableMongoRepositories
public class MonitoringDemoApplication {

    public static final String COMMA_DELIMITER = ",";
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    @Value("classpath:product_catalog.csv")
    private Resource resourceFile;

    public static void main(String[] args) {
        SpringApplication.run(MonitoringDemoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void importProductsFromFileToDB() {
        if (!productRepository.findAll().iterator().hasNext()) {
            List<Product> products = new ArrayList<>();
            List<Inventory> productInventories = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(resourceFile.getInputStream()))) {

                br.readLine(); //skip header
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(COMMA_DELIMITER);

                    products.add(new Product(values[0], values[1], values[2]));
                    productInventories.add(new Inventory(values[0], 0));

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            inventoryRepository.saveAll(productInventories);
            productRepository.saveAll(products);
        }
    }
}
