package com.company.NuttyDelightsBackend.repository;

import com.company.NuttyDelightsBackend.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category,String> {

}
