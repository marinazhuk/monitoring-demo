package com.highload.course.monitoringdemo.controller;

import com.highload.course.monitoringdemo.model.Inventory;
import com.highload.course.monitoringdemo.model.Product;
import com.highload.course.monitoringdemo.repository.InventoryRepository;
import com.highload.course.monitoringdemo.repository.ProductRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.statsd.StatsdMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class DemoRestController {
    public static final Logger logger = LoggerFactory.getLogger(DemoRestController.class);
    public static final String REQUEST_SUCCESSFUL_COUNT_METRIC = "performance.request.successful.count";
    public static final String REQUEST_SUCCESSFUL_TIME_METRIC = "performance.request.successful.time";
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final Counter counter;
    private final Timer timer;
    private final Random random = new Random();

    @Value("${categories_key_words}")
    private List<String> categoriesKeyWords = new ArrayList<>();

    @Autowired
    public DemoRestController(ProductRepository productRepository,
                              InventoryRepository inventoryRepository, StatsdMeterRegistry meterRegistry) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.counter = meterRegistry.counter(REQUEST_SUCCESSFUL_COUNT_METRIC);
        this.timer = meterRegistry.timer(REQUEST_SUCCESSFUL_TIME_METRIC);
    }

    @GetMapping("/products")
    public String updateProductsInventory() {

        timer.record(() -> {
                    String categoryKeyWord =
                            categoriesKeyWords.get(random.nextInt(categoriesKeyWords.size()));

                    List<Product> products =
                            productRepository.findByCategoryContaining(categoryKeyWord);

                    products.forEach(product -> {
                        Inventory productInventory =
                                inventoryRepository.findByProductCode(product.getProductCode());
                        productInventory.setQuantity(random.nextInt(1000));
                        inventoryRepository.save(productInventory);

                        logger.info("Inventory {} was updated for product {}",
                                productInventory.getQuantity(),  product);
                    });

                    counter.increment();
                }
        );
        logger.info(REQUEST_SUCCESSFUL_COUNT_METRIC + "=" + counter.count());
        logger.info(REQUEST_SUCCESSFUL_TIME_METRIC + "=" + timer.totalTime(TimeUnit.MILLISECONDS) + " ms");
        return "Inventory for products was updated";
    }
}
