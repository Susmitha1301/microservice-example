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

        System.out.println("Kafka event publishing started: cartId="
                + event.getCartId()
                + ", productId=" + event.getProductId()
                + ", quantity=" + event.getQuantity());

        kafkaTemplate.send(TOPIC, event);

        System.out.println("Kafka event sent to topic: " + TOPIC);
    }
}