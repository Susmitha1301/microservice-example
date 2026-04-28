package com.example.cart_service.controller;

import com.example.cart_service.entity.Cart;
import com.example.cart_service.entity.CartItem;
import com.example.cart_service.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/test")
    public String testCart() {
        return "Cart controller is working";
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        System.out.println("API called: POST /cart");
        return cartService.createCart(cart);
    }

    @PostMapping("/add")
    public String addItemToCart(@Valid @RequestBody CartItem item) {
        System.out.println("API called: POST /cart/add");
        return cartService.addItemToCart(item);
    }
}