package com.nhom4.vanphongphamonline.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document
public class HoaDon {
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
}
