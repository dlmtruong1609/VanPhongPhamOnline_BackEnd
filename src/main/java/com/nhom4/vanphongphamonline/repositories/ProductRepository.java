package com.nhom4.vanphongphamonline.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.models.Product;


public interface ProductRepository extends MongoRepository<Product, String>{
	// db.product.ensureIndex({ name: "text", description : "text", category : "text" });
	@Query("{'$text': {'$search':?0}}") // cần create index trước 
	public Page<Product> findByTextSearch(String keyword, Pageable pageable);
	
	public Page<Product> findByCategory_Name(Pageable pageable, String name);
	
}