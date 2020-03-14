package com.nhom4.vanphongphamonline.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
	@Query("{'customer.account.username': ?0}")
	public List<Order> getAllOrderByUsername(String username);
}
