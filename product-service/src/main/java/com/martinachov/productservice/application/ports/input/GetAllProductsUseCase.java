package com.martinachov.productservice.application.ports.input;

import java.util.List;

import com.martinachov.productservice.domain.model.Product;

public interface GetAllProductsUseCase {
    List<Product> getAllProducts();
}
