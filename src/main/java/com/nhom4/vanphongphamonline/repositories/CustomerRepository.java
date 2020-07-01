package com.nhom4.vanphongphamonline.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.models.Account;
import com.nhom4.vanphongphamonline.models.Customer;
import com.nhom4.vanphongphamonline.models.Product;

public interface CustomerRepository extends MongoRepository<Customer, String> {
	public Customer findByAccount_Username(String username);
	
	public Customer findByAccount_Email(String email);
	
	@Query("{'$text': {'$search':?0}}") // Không phân trang db.customers.ensureIndex({name: "text", 'account.username': "text", 'account.email': "text"})
	public List<Customer> findByTextSearch(String keyword);
}
