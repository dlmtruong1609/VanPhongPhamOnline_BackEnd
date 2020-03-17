package com.nhom4.vanphongphamonline.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nhom4.vanphongphamonline.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
	public List<Order> getAllOrderByCustomer_Account_Username(String username);
}
