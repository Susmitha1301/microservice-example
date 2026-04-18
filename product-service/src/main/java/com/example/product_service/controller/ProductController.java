package com.example.product_service.controller;

import com.example.product_service.entity.Product;
import com.example.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/page")
    public Page<Product> getProductsWithPaginationAndSorting(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy) {
        return productService.getProductsWithPaginationAndSorting(page, size, sortBy);
    }

    @GetMapping("/in-stock")
    public List<Product> getProductsInStock() {
        return productService.getProductsInStock();
    }

    @GetMapping("/names")
    public List<String> getAllProductNames() {
        return productService.getAllProductNames();
    }

    @GetMapping("/price/{price}")
    public List<Product> getProductsAbovePrice(@PathVariable Double price) {
        return productService.getProductsAbovePrice(price);
    }
}