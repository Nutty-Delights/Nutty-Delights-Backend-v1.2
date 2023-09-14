package com.company.NuttyDelightsBackend.repository;


import com.company.NuttyDelightsBackend.model.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken , String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
    ConfirmationToken findByUserId(String userId);

    void deleteByUserId(String userId);
    void deleteAllByUserId(String userId);
}
