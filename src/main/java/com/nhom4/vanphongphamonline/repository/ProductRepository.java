package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.model.Product;


public interface ProductRepository extends MongoRepository<Product, String>{
	// db.product.ensureIndex({ name: "text", description : "text", category : "text" });
	@Query("{'$text': {'$search':?0}}") // cần create index trước 
	public Page<Product> findByTextSearch(String keyword, Pageable pageable);
	
	public Page<Product> findByCategory_Id(Pageable pageable, String id);
	
}