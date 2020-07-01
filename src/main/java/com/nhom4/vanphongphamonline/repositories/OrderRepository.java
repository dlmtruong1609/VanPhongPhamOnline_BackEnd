package com.nhom4.vanphongphamonline.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.models.Customer;
import com.nhom4.vanphongphamonline.models.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
	public List<Order> getAllOrderByCustomer_Account_Username(String username);

	@Query("{'$text': {'$search':?0}}") // Không phân trang db.orders.ensureIndex({totalMoney: "text", 'customer.name': "text", 'customer.account.username': "text"})
	public List<Order> findByTextSearch(String keyword);
}
