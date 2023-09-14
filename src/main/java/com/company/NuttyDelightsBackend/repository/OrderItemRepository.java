package com.company.NuttyDelightsBackend.repository;

import com.company.NuttyDelightsBackend.model.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderItemRepository extends MongoRepository<OrderItem , String> {
}
