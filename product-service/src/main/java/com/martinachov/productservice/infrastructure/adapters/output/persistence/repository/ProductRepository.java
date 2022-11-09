package com.martinachov.productservice.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.martinachov.productservice.infrastructure.adapters.output.persistence.entity.ProductEntity;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {
    
}
