package com.example.cart_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cartId;

    private Long productId;

    @NotNull(message = "Quantity should not be null")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;
}