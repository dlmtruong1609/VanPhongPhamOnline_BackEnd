package com.nhom4.vanphongphamonline.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nhom4.vanphongphamonline.models.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
	public List<Order> getAllOrderByCustomer_Account_Username(String username);
}
