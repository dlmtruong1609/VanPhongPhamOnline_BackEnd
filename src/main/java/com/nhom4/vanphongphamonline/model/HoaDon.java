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
	@DBRef // vì danh sách hiển thị nhiều nên dùng dbref để hạn chế hiển thị thông tin quá dài khi show ra
	private List<ChiTietHoaDon> danhsachCTHD;
	@Field
	private KhachHang khachHang;
	@Field
	private DiaChi diaChi;
	@Field
	private String ghiChu;
	@Field
	private String cachThucThanhToan;
	
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
	public DiaChi getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(DiaChi diaChi) {
		this.diaChi = diaChi;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	public String getCachThucThanhToan() {
		return cachThucThanhToan;
	}
	public void setCachThucThanhToan(String cachThucThanhToan) {
		this.cachThucThanhToan = cachThucThanhToan;
	}
	
	
	public HoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HoaDon(String maHoaDon, LocalDate ngayLapHoaDon, double tongTien, List<ChiTietHoaDon> danhsachCTHD,
			KhachHang khachHang, DiaChi diaChi, String ghiChu, String cachThucThanhToan) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayLapHoaDon = ngayLapHoaDon;
		this.tongTien = tongTien;
		this.danhsachCTHD = danhsachCTHD;
		this.khachHang = khachHang;
		this.diaChi = diaChi;
		this.ghiChu = ghiChu;
		this.cachThucThanhToan = cachThucThanhToan;
	}
	
	@Override
	public String toString() {
		return "HoaDon [maHoaDon=" + maHoaDon + ", ngayLapHoaDon=" + ngayLapHoaDon + ", tongTien=" + tongTien
				+ ", danhsachCTHD=" + danhsachCTHD + ", khachHang=" + khachHang + ", diaChi=" + diaChi + ", ghiChu=" + ghiChu + ", cachThucThanhToan=" + cachThucThanhToan + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maHoaDon == null) ? 0 : maHoaDon.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		if (maHoaDon == null) {
			if (other.maHoaDon != null)
				return false;
		} else if (!maHoaDon.equals(other.maHoaDon))
			return false;
		return true;
	}
	
	
}
