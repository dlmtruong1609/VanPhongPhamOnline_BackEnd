package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.model.TaiKhoan;

public interface TaiKhoanRepository extends MongoRepository<TaiKhoan, String>{
	@Query("{'taiKhoan': ?0}")
	public TaiKhoan findByUsername(String username);
	@Query("{'email': ?0}")
	public TaiKhoan findByEmail(String email);
}
