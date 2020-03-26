package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.model.Category;
import com.nhom4.vanphongphamonline.model.Product;

public interface CategoryRepository extends MongoRepository<Category, String> {
	// db.category.ensureIndex({ name: "text"});
	@Query("{'$text': {'$search':?0}}") // cần create index trước 
	public Page<Category> findByTextSearch(String keyword, Pageable pageable);
}
