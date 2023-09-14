package com.company.NuttyDelightsBackend.repository;

import com.company.NuttyDelightsBackend.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product,String> {
        Optional<List> findByProductCategoryId(String productCategoryId);
}
