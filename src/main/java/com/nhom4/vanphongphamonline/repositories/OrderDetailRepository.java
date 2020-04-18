package com.nhom4.vanphongphamonline.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nhom4.vanphongphamonline.models.OrderDetail;

public interface OrderDetailRepository extends MongoRepository<OrderDetail, String>{

}
