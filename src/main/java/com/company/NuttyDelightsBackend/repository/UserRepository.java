package com.company.NuttyDelightsBackend.repository;

import com.company.NuttyDelightsBackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User , String> {
    public User findByEmail(String email);
    public User findByMobileNumber(String mobileNumber);
    User findByEmailIgnoreCase(String emailId);

}
