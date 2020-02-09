package com.nhom4.vanphongphamonline.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class HoaDon {
	@GeneratedValue
	@Id
	private String maHoaDon;
	@Field
	private LocalDate ngayLapHoaDon;
	@Field
	private double tongTien;
	@DBRef
	private List<ChiTietHoaDon> danhsachCTHD;
	@DBRef
	private KhachHang khachHang;
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public LocalDate getNgayLapHoaDon() {
		return ngayLapHoaDon;
	}
	public void setNgayLapHoaDon(LocalDate ngayLapHoaDon) {
		this.ngayLapHoaDon = ngayLapHoaDon;
	}
	public double getTongTien() {
		return tongTien;
	}
	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}
	public List<ChiTietHoaDon> getDanhsachCTHD() {
		return danhsachCTHD;
	}
	public void setDanhsachCTHD(List<ChiTietHoaDon> danhsachCTHD) {
		this.danhsachCTHD = danhsachCTHD;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public HoaDon(String maHoaDon, LocalDate ngayLapHoaDon, double tongTien, List<ChiTietHoaDon> danhsachCTHD,
			KhachHang khachHang) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayLapHoaDon = ngayLapHoaDon;
		this.tongTien = tongTien;
		this.danhsachCTHD = danhsachCTHD;
		this.khachHang = khachHang;
	}
	public HoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "HoaDon [maHoaDon=" + maHoaDon + ", ngayLapHoaDon=" + ngayLapHoaDon + ", tongTien=" + tongTien
				+ ", danhsachCTHD=" + danhsachCTHD + ", khachHang=" + khachHang + "]";
	}
	
}
