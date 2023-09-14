package com.company.NuttyDelightsBackend.repository;

import com.company.NuttyDelightsBackend.model.CartItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartItemRepository extends MongoRepository<CartItem , String> {
    CartItem findByCartItemVariantIdAndCartItemUserId(String variantId , String userId);
}
