package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nhom4.vanphongphamonline.model.Image;

public interface ImageRepository extends MongoRepository<Image, String>{

}
