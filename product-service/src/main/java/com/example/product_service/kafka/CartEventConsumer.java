package com.example.product_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CartEventConsumer {

    @KafkaListener(topics = "cart-topic", groupId = "product-service-group-test-1")
    public void listenCartEvent(String message) {
        System.out.println("=================================");
        System.out.println("PRODUCT SERVICE RECEIVED EVENT:");
        System.out.println(message);
        System.out.println("=================================");
    }
}