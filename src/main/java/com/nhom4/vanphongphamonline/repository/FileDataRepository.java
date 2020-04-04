package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nhom4.vanphongphamonline.model.FileData;

public interface FileDataRepository extends MongoRepository<FileData, String> {
	
}
