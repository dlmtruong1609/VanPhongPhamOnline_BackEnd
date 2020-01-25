package com.nhom4.vanphongphamonline.model;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Role {
	@Field
	private Long id;
	@Field
	private String tenRole;
	@DBRef
	private Set<TaiKhoan> taiKhoans;
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
	public Set<TaiKhoan> getTaiKhoans() {
		return taiKhoans;
	}
	public void setTaiKhoans(Set<TaiKhoan> taiKhoans) {
		this.taiKhoans = taiKhoans;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", tenRole=" + tenRole + ", taiKhoans=" + taiKhoans + "]";
	}
	public Role(Long id, String tenRole, Set<TaiKhoan> taiKhoans) {
		super();
		this.id = id;
		this.tenRole = tenRole;
		this.taiKhoans = taiKhoans;
	}
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
