package com.martinachov.productservice.infrastructure.adapters.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.martinachov.productservice.domain.service.ProductService;
import com.martinachov.productservice.infrastructure.adapters.output.persistence.ProductPersistenceAdapter;
import com.martinachov.productservice.infrastructure.adapters.output.persistence.repository.ProductRepository;

/**
 * Configuracion BEANS
 */
@Configuration
public class BeansConfiguration {
 
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public ProductPersistenceAdapter productPersistenceAdapter(ProductRepository productRepository, ModelMapper modelMapper) {
        return new ProductPersistenceAdapter(productRepository, modelMapper);
    }

    @Bean
    public ProductService productService(ProductPersistenceAdapter productPersistenceAdapter) {
        return new ProductService(productPersistenceAdapter);
    }

}
