package com.nhom4.vanphongphamonline.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nhom4.vanphongphamonline.model.NhaCungCap;

@Repository
public interface NhaCungCapRepository extends MongoRepository<NhaCungCap, String> {
	
}
