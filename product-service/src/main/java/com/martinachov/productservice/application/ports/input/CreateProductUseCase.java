package com.martinachov.productservice.application.ports.input;

import com.martinachov.productservice.domain.model.Product;

public interface CreateProductUseCase {
    void createProduct(Product product);
}
