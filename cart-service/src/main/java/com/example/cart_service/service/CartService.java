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

import java.util.concurrent.CompletableFuture;

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
        System.out.println("Creating cart for userId: " + cart.getUserId());
        return cartRepository.save(cart);
    }

    public String addItemToCart(CartItem item) {

        System.out.println("Add item request started. cartId="
                + item.getCartId()
                + ", productId=" + item.getProductId()
                + ", quantity=" + item.getQuantity());

        CompletableFuture<ProductDTO> productFuture =
                fetchProductAsync(item.getProductId());

        CompletableFuture<Boolean> validationFuture =
                productFuture.thenCompose(product ->
                        validateStockAsync(product, item.getQuantity())
                );

        ProductDTO product = productFuture.join();
        Boolean isStockValid = validationFuture.join();

        if (product == null) {
            System.out.println("Product not found for productId: " + item.getProductId());
            return "Product not found";
        }

        if (!isStockValid) {
            System.out.println("Insufficient stock for productId: " + item.getProductId());
            return "Insufficient stock";
        }

        cartItemRepository.save(item);
        System.out.println("Cart item saved successfully");

        CartEvent event = new CartEvent();
        event.setCartId(item.getCartId());
        event.setProductId(item.getProductId());
        event.setQuantity(item.getQuantity());

        cartProducer.sendEvent(event);

        System.out.println("Add item request completed successfully");

        return "Item added to cart successfully using CompletableFuture and event published to Kafka";
    }

    public CompletableFuture<ProductDTO> fetchProductAsync(Long productId) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching product asynchronously on thread: "
                    + Thread.currentThread().getName());

            ProductDTO product = webClient.get()
                    .uri("http://localhost:8081/products/" + productId)
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();

            if (product == null) {
                throw new RuntimeException("Product not found");
            }

            return product;
        });
    }

    public CompletableFuture<Boolean> validateStockAsync(ProductDTO product, Integer quantity) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Validating stock asynchronously on thread: "
                    + Thread.currentThread().getName());

            return product.getStock() >= quantity;
        });
    }
}