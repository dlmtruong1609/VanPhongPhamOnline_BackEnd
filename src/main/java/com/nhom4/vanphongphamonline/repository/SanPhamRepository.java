package com.nhom4.vanphongphamonline.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.model.SanPham;


public interface SanPhamRepository extends MongoRepository<SanPham, String>{
	@Query("{'$text': {'$search':?0}}")
	public List<SanPham> findByProductName(String name);
}
