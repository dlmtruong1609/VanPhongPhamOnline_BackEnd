package com.nhom4.vanphongphamonline.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
public class ChiTietHoaDon {
	@DBRef
	private SanPham sanPham;
	@Field
	private double donGia;
	@Field
	private int soLuong;
	public SanPham getSanPham() {
		return sanPham;
	}
	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}
	public double getDonGia() {
		return donGia;
	}
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public ChiTietHoaDon(SanPham sanPham, double donGia, int soLuong) {
		super();
		this.sanPham = sanPham;
		this.donGia = donGia;
		this.soLuong = soLuong;
	}
	public ChiTietHoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ChiTietHoaDon [sanPham=" + sanPham + ", donGia=" + donGia + ", soLuong=" + soLuong + "]";
	}
	
}
