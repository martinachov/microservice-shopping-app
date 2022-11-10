package com.martinachov.productservice.infrastructure.adapters.output.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.martinachov.productservice.application.ports.output.ProductOutputPort;
import com.martinachov.productservice.domain.model.Product;
import com.martinachov.productservice.infrastructure.adapters.output.persistence.entity.ProductEntity;
import com.martinachov.productservice.infrastructure.adapters.output.persistence.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ProductPersistenceAdapter implements ProductOutputPort{

    private final ProductRepository productRepository;
    private final ModelMapper productMapper;

    @Override
    public void saveProduct(Product product) {
        log.info("Guardando producto: {} ..." , product.getDescription());
        ProductEntity productEntity = productMapper.map(product, ProductEntity.class);
        productRepository.save(productEntity);
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Retornando todos los productos de la BD");
        List<ProductEntity> response = productRepository.findAll();
        return response.stream()
                        .map(productEntity -> productMapper.map(productEntity, Product.class))
                        .collect(Collectors.toList());
    }
    
}
