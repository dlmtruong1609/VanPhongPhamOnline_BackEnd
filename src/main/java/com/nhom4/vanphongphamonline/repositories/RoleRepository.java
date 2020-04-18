package com.nhom4.vanphongphamonline.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.nhom4.vanphongphamonline.models.Role;

import antlr.collections.List;

public interface RoleRepository extends MongoRepository<Role, String> {
	public java.util.List<Role> findByName(String name);
}
