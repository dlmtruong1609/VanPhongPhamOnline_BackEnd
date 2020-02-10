package com.nhom4.vanphongphamonline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.model.Role;

import antlr.collections.List;

public interface RoleRepository extends MongoRepository<Role, Long> {
	@Query("{'tenRole': ?0}")
	public java.util.List<Role> findByName(String name);
}
