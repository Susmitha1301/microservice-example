package com.example.cart_service.kafka;

import com.example.cart_service.event.CartEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CartProducer {

    private static final String TOPIC = "cart-topic";

    @Autowired
    private KafkaTemplate<String, CartEvent> kafkaTemplate;

    public void sendEvent(CartEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}