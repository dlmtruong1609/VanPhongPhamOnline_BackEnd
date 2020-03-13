package com.nhom4.vanphongphamonline.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.model.HoaDon;

public interface HoaDonRepository extends MongoRepository<HoaDon, String> {
	@Query("{'khachHang.taiKhoan.taiKhoan': ?0}")
	public List<HoaDon> getAllOrderByUsername(String username);
}
