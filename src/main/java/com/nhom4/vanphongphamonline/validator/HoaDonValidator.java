package com.nhom4.vanphongphamonline.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nhom4.vanphongphamonline.model.TaiKhoan;
@Component
public class HoaDonValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return TaiKhoan.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		try {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tongTien", "Chưa thành tiền", "1");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ngayLapHoaDon", "Chưa có ngày tạo", "2");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "khachHang", "Hoá đơn chưa có khách hàng", "3");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "danhSachCTHD", "Chưa có sản phẩm cần thanh toán", "4");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
