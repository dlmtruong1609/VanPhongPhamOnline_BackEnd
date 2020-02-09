package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nhom4.vanphongphamonline.model.HoaDon;

public interface HoaDonRepository extends MongoRepository<HoaDon, String> {

}
