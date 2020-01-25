package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nhom4.vanphongphamonline.model.KhachHang;

public interface KhachHangRepository extends MongoRepository<KhachHang, String> {

}
