package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.model.Customer;
import com.nhom4.vanphongphamonline.model.Account;

public interface CustomerRepository extends MongoRepository<Customer, String> {
	@Query("{'account.username': ?0}")
	public Customer findByUsername(String username);
	@Query("{'account.email': ?0}")
	public Customer findByEmail(String email);
}
