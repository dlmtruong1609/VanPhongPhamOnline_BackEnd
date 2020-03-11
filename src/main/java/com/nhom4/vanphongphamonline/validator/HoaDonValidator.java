package com.nhom4.vanphongphamonline.validator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nhom4.vanphongphamonline.model.ChiTietHoaDon;
import com.nhom4.vanphongphamonline.model.TaiKhoan;
import com.nhom4.vanphongphamonline.services.ServiceStatus;
@Component
public class HoaDonValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return TaiKhoan.class.equals(clazz);
	}

	public void validateCTHD(Object target, Errors errors) {
		// TODO Auto-generated method stub
		try {
			ChiTietHoaDon chiTietHoaDon = (ChiTietHoaDon) target;
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tongTien", "Chưa thành tiền", "1");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ngayLapHoaDon", "Chưa có ngày tạo", "2");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "khachHang", "Hoá đơn chưa có khách hàng", "3");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "danhSachCTHD", "Chưa có sản phẩm cần thanh toán", "4");
//			
			int soLuongTon = chiTietHoaDon.getSanPham().getSoLuongTon();
			int soLuongDat = chiTietHoaDon.getSoLuong();
			if(soLuongDat <= 0) {
				errors.rejectValue("danhsachCTHD", "Số lượng phải lớn hơn hoặc bằng 1", "1");
			} else if(soLuongDat > soLuongTon) {
				errors.rejectValue("danhsachCTHD",  "Số lượng phải nhỏ hơn hoặc bằng số của sản phẩm là " + soLuongTon, "2");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
