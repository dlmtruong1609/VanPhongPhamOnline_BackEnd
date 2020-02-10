package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.model.KhachHang;
import com.nhom4.vanphongphamonline.model.TaiKhoan;

public interface KhachHangRepository extends MongoRepository<KhachHang, String> {
	@Query("{'taiKhoan.taiKhoan': ?0}")
	public KhachHang findByUsername(String username);
	@Query("{'taiKhoan.email': ?0}")
	public KhachHang findByEmail(String email);
}
