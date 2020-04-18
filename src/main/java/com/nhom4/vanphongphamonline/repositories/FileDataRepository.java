package com.nhom4.vanphongphamonline.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nhom4.vanphongphamonline.models.FileData;

public interface FileDataRepository extends MongoRepository<FileData, String> {
	
}
