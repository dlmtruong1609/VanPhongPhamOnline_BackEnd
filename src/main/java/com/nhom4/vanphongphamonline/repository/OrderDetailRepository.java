package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nhom4.vanphongphamonline.model.OrderDetail;

public interface OrderDetailRepository extends MongoRepository<OrderDetail, String>{

}
