package com.nhom4.vanphongphamonline.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class LoaiSanPham {
	@Id
	private String maLoaiSanPham;
	@Field
	private String tenLoaiSanPham;
	public String getMaLoaiSanPham() {
		return maLoaiSanPham;
	}
	public void setMaLoaiSanPham(String maLoaiSanPham) {
		this.maLoaiSanPham = maLoaiSanPham;
	}
	public String getTenLoaiSanPham() {
		return tenLoaiSanPham;
	}
	public void setTenLoaiSanPham(String tenLoaiSanPham) {
		this.tenLoaiSanPham = tenLoaiSanPham;
	}
	@Override
	public String toString() {
		return "LoaiSanPham [maLoaiSanPham=" + maLoaiSanPham + ", tenLoaiSanPham=" + tenLoaiSanPham + "]";
	}
	public LoaiSanPham(String maLoaiSanPham, String tenLoaiSanPham) {
		super();
		this.maLoaiSanPham = maLoaiSanPham;
		this.tenLoaiSanPham = tenLoaiSanPham;
	}
	public LoaiSanPham() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
