package com.martinachov.productservice.application.ports.output;

import java.util.List;

import com.martinachov.productservice.domain.model.Product;

public interface ProductOutputPort {
    void saveProduct(Product product);
    List<Product> getAllProducts();
}
