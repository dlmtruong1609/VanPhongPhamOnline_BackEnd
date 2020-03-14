package com.nhom4.vanphongphamonline.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.model.Product;


public interface ProductRepository extends MongoRepository<Product, String>{
	@Query("{'$text': {'$search':?0}}") // cần create index trước
	public List<Product> findByProductName(String name);
}
