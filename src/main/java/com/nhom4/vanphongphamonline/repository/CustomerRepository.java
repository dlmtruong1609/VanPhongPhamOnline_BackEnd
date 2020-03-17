package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.model.Customer;
import com.nhom4.vanphongphamonline.model.Account;

public interface CustomerRepository extends MongoRepository<Customer, String> {
	public Customer findByAccount_Username(String username);
	
	public Customer findByAccount_Email(String email);
}
