package com.nhom4.vanphongphamonline.model;

import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "SanPham")
public class SanPham {
	@GeneratedValue
	@Id
	private String maSanPham;
	
	@Field
	private String tenSanPham;
	@Field
	private String moTa;
	@Field
	private double giaSanPham;
	@Field
	private int soLuongTon;
	@Field
	private NhaCungCap nhaCungCap;
	@DBRef
	private LoaiSanPham loaiSanPham;
	
	public LoaiSanPham getLoaiSanPham() {
		return loaiSanPham;
	}
	public void setLoaiSanPham(LoaiSanPham loaiSanPham) {
		this.loaiSanPham = loaiSanPham;
	}
	public String getMaSanPham() {
		return maSanPham;
	}
	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}
	public String getTenSanPham() {
		return tenSanPham;
	}
	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public double getGiaSanPham() {
		return giaSanPham;
	}
	public void setGiaSanPham(double giaSanPham) {
		this.giaSanPham = giaSanPham;
	}
	public int getSoLuongTon() {
		return soLuongTon;
	}
	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
	}
	public NhaCungCap getNhaCungCap() {
		return nhaCungCap;
	}
	public void setNhaCungCap(NhaCungCap nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}

	public SanPham(String maSanPham, String tenSanPham, String moTa, double giaSanPham, int soLuongTon,
			NhaCungCap nhaCungCap, LoaiSanPham loaiSanPham) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.moTa = moTa;
		this.giaSanPham = giaSanPham;
		this.soLuongTon = soLuongTon;
		this.nhaCungCap = nhaCungCap;
		this.loaiSanPham = loaiSanPham;
	}
	public SanPham() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "SanPham [maSanPham=" + maSanPham + ", tenSanPham=" + tenSanPham + ", moTa=" + moTa + ", giaSanPham="
				+ giaSanPham + ", soLuongTon=" + soLuongTon + ", nhaCungCap=" + nhaCungCap + ", loaiSanPham="
				+ loaiSanPham + "]";
	}
	
	
}
