package com.example.cart_service.service;

import com.example.cart_service.dto.ProductDTO;
import com.example.cart_service.entity.Cart;
import com.example.cart_service.entity.CartItem;
import com.example.cart_service.event.CartEvent;
import com.example.cart_service.kafka.CartProducer;
import com.example.cart_service.repository.CartItemRepository;
import com.example.cart_service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private WebClient webClient;

    @Autowired
    private CartProducer cartProducer;

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public String addItemToCart(CartItem item) {

        ProductDTO product = webClient.get()
                .uri("http://localhost:8081/products/" + item.getProductId())
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .block();

        if (product == null) {
            return "Product not found";
        }

        if (product.getStock() < item.getQuantity()) {
            return "Insufficient stock";
        }

        cartItemRepository.save(item);

        CartEvent event = new CartEvent();
        event.setCartId(item.getCartId());
        event.setProductId(item.getProductId());
        event.setQuantity(item.getQuantity());

        cartProducer.sendEvent(event);

        return "Item added to cart successfully and event published to Kafka";
    }
}