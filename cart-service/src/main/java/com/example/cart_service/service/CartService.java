package com.example.cart_service.service;

import com.example.cart_service.entity.Cart;
import com.example.cart_service.entity.CartItem;
import com.example.cart_service.repository.CartItemRepository;
import com.example.cart_service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart createCart(Cart cart){
        return cartRepository.save(cart);
    }
    public CartItem addItemToCart(CartItem item){
        return cartItemRepository.save(item);
    }
}
