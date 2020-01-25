package com.nhom4.vanphongphamonline.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document
public class KhachHang {
	@Id
	private String maKhachHang;
	@Field
	private String tenKhachHang;
	@Field
	private String diaChi;
	@Field
	private String dienThoai;
	@Field
	private String cmnd;
	@Field
	private String email;
	@Field
	private LocalDate ngaySinh;
	@DBRef
	private TaiKhoan taiKhoan; 
	
	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}
	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	public String getMaKhachHang() {
		return maKhachHang;
	}
	public void setMaKhachHang(String maKhachHang) {
		this.maKhachHang = maKhachHang;
	}
	public String getTenKhachHang() {
		return tenKhachHang;
	}
	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getDienThoai() {
		return dienThoai;
	}
	public void setDienThoai(String dienThoai) {
		this.dienThoai = dienThoai;
	}
	public String getCmnd() {
		return cmnd;
	}
	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public KhachHang() {
		super();
		// TODO Auto-generated constructor stub
	}
	public KhachHang(String maKhachHang, String tenKhachHang, String diaChi, String dienThoai, String cmnd,
			String email, LocalDate ngaySinh, TaiKhoan taiKhoan) {
		super();
		this.maKhachHang = maKhachHang;
		this.tenKhachHang = tenKhachHang;
		this.diaChi = diaChi;
		this.dienThoai = dienThoai;
		this.cmnd = cmnd;
		this.email = email;
		this.ngaySinh = ngaySinh;
		this.taiKhoan = taiKhoan;
	}
	@Override
	public String toString() {
		return "KhachHang [maKhachHang=" + maKhachHang + ", tenKhachHang=" + tenKhachHang + ", diaChi=" + diaChi
				+ ", dienThoai=" + dienThoai + ", cmnd=" + cmnd + ", email=" + email + ", ngaySinh=" + ngaySinh
				+ ", taiKhoan=" + taiKhoan + "]";
	}
	
	
}
