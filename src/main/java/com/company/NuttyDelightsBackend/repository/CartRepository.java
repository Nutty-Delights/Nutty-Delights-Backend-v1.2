package com.company.NuttyDelightsBackend.repository;

import com.company.NuttyDelightsBackend.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends MongoRepository<Cart, String> {
//    public Cart findByUserId(@Param("userId")String userId);
    public Cart findByCartUserId(String userId);
}
