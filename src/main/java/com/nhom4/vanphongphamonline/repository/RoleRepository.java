package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.nhom4.vanphongphamonline.model.Role;

public interface RoleRepository extends MongoRepository<Role, Long> {
	
}
