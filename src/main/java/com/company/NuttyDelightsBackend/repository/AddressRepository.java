package com.company.NuttyDelightsBackend.repository;

import com.company.NuttyDelightsBackend.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address , String> {
}
