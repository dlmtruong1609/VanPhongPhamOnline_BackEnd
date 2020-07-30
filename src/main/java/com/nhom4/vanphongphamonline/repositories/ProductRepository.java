package com.nhom4.vanphongphamonline.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.models.Product;


public interface ProductRepository extends MongoRepository<Product, String>{
	// db.products.ensureIndex({ name: "text", description : "text", category : "text" });
	@Query("{'$text': {'$search':?0}}") // cần create index trước, có phân trang
	public Page<Product> findByTextSearch(String keyword, Pageable pageable);
	@Query("{'$text': {'$search':?0}}") // Không phân trang
	public List<Product> findByTextSearch(String keyword);
	public Page<Product> findByCategory_Name(Pageable pageable, String name);
	
}