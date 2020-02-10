package com.nhom4.vanphongphamonline.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DiaChi {
	private String tinhThanhPho;
	private String quanHuyen;
	private String phuongXa;
	private String khuPho;
	private String duongSoNha;
	public String getTinhThanhPho() {
		return tinhThanhPho;
	}
	public void setTinhThanhPho(String tinhThanhPho) {
		this.tinhThanhPho = tinhThanhPho;
	}
	public String getQuanHuyen() {
		return quanHuyen;
	}
	public void setQuanHuyen(String quanHuyen) {
		this.quanHuyen = quanHuyen;
	}
	public String getPhuongXa() {
		return phuongXa;
	}
	public void setPhuongXa(String phuongXa) {
		this.phuongXa = phuongXa;
	}
	public String getKhuPho() {
		return khuPho;
	}
	public void setKhuPho(String khuPho) {
		this.khuPho = khuPho;
	}
	public String getDuongSoNha() {
		return duongSoNha;
	}
	public void setDuongSoNha(String duongSoNha) {
		this.duongSoNha = duongSoNha;
	}
	public DiaChi(String tinhThanhPho, String quanHuyen, String phuongXa, String khuPho, String duongSoNha) {
		super();
		this.tinhThanhPho = tinhThanhPho;
		this.quanHuyen = quanHuyen;
		this.phuongXa = phuongXa;
		this.khuPho = khuPho;
		this.duongSoNha = duongSoNha;
	}
	public DiaChi() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "DiaChi [tinhThanhPho=" + tinhThanhPho + ", quanHuyen=" + quanHuyen + ", phuongXa=" + phuongXa
				+ ", khuPho=" + khuPho + ", duongSoNha=" + duongSoNha + "]";
	}
	
}
