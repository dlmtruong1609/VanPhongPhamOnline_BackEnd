package com.nhom4.vanphongphamonline.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.models.Account;
import com.nhom4.vanphongphamonline.models.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
	public Customer findByAccount_Username(String username);
	
	public Customer findByAccount_Email(String email);
}
