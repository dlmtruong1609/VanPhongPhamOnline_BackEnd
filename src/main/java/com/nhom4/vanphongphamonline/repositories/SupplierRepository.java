package com.nhom4.vanphongphamonline.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.nhom4.vanphongphamonline.models.Category;
import com.nhom4.vanphongphamonline.models.Supplier;

@Repository
public interface SupplierRepository extends MongoRepository<Supplier, String> {
	// db.supplier.ensureIndex({ name: "text", description: "text"});
	@Query("{'$text': {'$search':?0}}") // cần create index trước 
	public Page<Supplier> findByTextSearch(String keyword, Pageable pageable);
}
