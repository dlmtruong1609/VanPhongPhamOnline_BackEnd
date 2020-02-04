package com.nhom4.vanphongphamonline.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("role")
public class Role {
	@Id
	private Long id;
	@Field
	private String tenRole;
//	@DBRef
//	private Set<TaiKhoan> taiKhoans;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTenRole() {
		return tenRole;
	}
	public void setTenRole(String tenRole) {
		this.tenRole = tenRole;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", tenRole=" + tenRole + ", taiKhoans=]";
	}

	
}
