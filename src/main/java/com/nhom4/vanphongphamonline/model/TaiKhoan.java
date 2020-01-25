package com.nhom4.vanphongphamonline.model;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document
public class TaiKhoan {
	@Field
	private String taiKhoan;
	@Field
	private String matKhau;
	@Field
	private String matKhauXacNhan;
	@DBRef
	private Set<Role> roles;
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getTaiKhoan() {
		return taiKhoan;
	}
	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	public String getMatKhauXacNhan() {
		return matKhauXacNhan;
	}
	public void setMatKhauXacNhan(String matKhauXacNhan) {
		this.matKhauXacNhan = matKhauXacNhan;
	}
	public TaiKhoan(String taiKhoan, String matKhau, String matKhauXacNhan) {
		super();
		this.taiKhoan = taiKhoan;
		this.matKhau = matKhau;
		this.matKhauXacNhan = matKhauXacNhan;
	}
	public TaiKhoan() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
