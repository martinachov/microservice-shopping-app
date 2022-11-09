package com.martinachov.productservice.domain.service;

import java.util.List;

import com.martinachov.productservice.application.ports.input.CreateProductUseCase;
import com.martinachov.productservice.application.ports.input.GetAllProductsUseCase;
import com.martinachov.productservice.application.ports.output.ProductOutputPort;
import com.martinachov.productservice.domain.model.Product;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductService implements CreateProductUseCase, GetAllProductsUseCase {

    private final ProductOutputPort productOutputPort;

    @Override
    public List<Product> getAllProducts() {
        return productOutputPort.getAllProducts();
    }

    @Override
    public void createProduct(Product product) {
        productOutputPort.saveProduct(product);
    }
   
}
