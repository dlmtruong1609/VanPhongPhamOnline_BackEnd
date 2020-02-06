package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.nhom4.vanphongphamonline.model.SanPham;

public interface SanPhamRepository extends MongoRepository<SanPham, String>{
}
