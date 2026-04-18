package com.example.product_service.service;

import com.example.product_service.entity.Product;
import com.example.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product){
        return productRepository.save(product);
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
}
public Product getProductById(Long id){
    Optional<Product> product= productRepository.findById(id);
    return product.orElse(null);
    }
    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setStock(updatedProduct.getStock());
            return productRepository.save(product);
        }
        return null;
    }
    public String deleteProduct(Long id) {
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return "Product deleted successfully";
        }
        return "product not foumd";
    }

    public Page<Product> getProductsWithPaginationAndSorting(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return productRepository.findAll(pageable);
    }

    public List<Product> getProductsInStock() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .filter(product -> product.getStock() > 50)
                .collect(Collectors.toList());
    }

    public List<String> getAllProductNames() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());
    }
}
