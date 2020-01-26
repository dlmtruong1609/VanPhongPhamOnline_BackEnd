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
	@Field
	private String email;
	@DBRef
	private Set<Role> roles;

	public TaiKhoan(String taiKhoan, String matKhau, String matKhauXacNhan, String email, Set<Role> roles) {
		super();
		this.taiKhoan = taiKhoan;
		this.matKhau = matKhau;
		this.matKhauXacNhan = matKhauXacNhan;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public TaiKhoan() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TaiKhoan [taiKhoan=" + taiKhoan + ", matKhau=" + matKhau + ", matKhauXacNhan=" + matKhauXacNhan
				+ ", email=" + email + ", roles=" + roles + "]";
	}
	
	
}
