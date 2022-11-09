package com.martinachov.productservice.infrastructure.adapters.input.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.martinachov.productservice.application.ports.input.CreateProductUseCase;
import com.martinachov.productservice.application.ports.input.GetAllProductsUseCase;
import com.martinachov.productservice.domain.model.Product;
import com.martinachov.productservice.infrastructure.adapters.input.rest.data.ProductRequest;
import com.martinachov.productservice.infrastructure.adapters.input.rest.data.ProductResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductRestAdapter {

    private final CreateProductUseCase createProductUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final ModelMapper productMapper;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        System.out.println("Recibiendo request para crear producto: " + productRequest);

        // Request to domain
        Product productToCreate = productMapper.map(productRequest, Product.class);
        createProductUseCase.createProduct(productToCreate);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        List<Product> allProducts = getAllProductsUseCase.getAllProducts();
        //Domain to Response
        return allProducts.stream()
            .map(product -> productMapper.map(product, ProductResponse.class))
            .collect(Collectors.toList());
    }

}
