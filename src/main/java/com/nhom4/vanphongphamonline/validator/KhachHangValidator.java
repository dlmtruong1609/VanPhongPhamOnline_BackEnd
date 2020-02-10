package com.nhom4.vanphongphamonline.validator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nhom4.vanphongphamonline.model.KhachHang;
import com.nhom4.vanphongphamonline.services.ServiceStatus;
@Component
public class KhachHangValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return KhachHang.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		KhachHang khachHang = (KhachHang) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenKhachHang", "Tên khách hàng không bỏ trống", "1");
		if(khachHang.getTenKhachHang().length() < 3 || khachHang.getTenKhachHang().length() > 50) {
			errors.rejectValue("tenKhachHang", "Tên khách hàng không được dưới 3 kí tự và không lớn hơn 50 kí tự", "3");
		}
		
		if(khachHang.getDienThoai() != null) {
			String regex = "^0[0-9]{9}$";
			if(khachHang.getDienThoai().matches(regex)!=true) {
				errors.rejectValue("dienThoai", "Sai định dạng số điện thoại", "4");
			}
		}
		String regex = "^[0-9]{9}$|^[0-9]{11}$";
		if(khachHang.getCmnd().matches(regex)!=true) {
			errors.rejectValue("cmnd", "Sai định dạng CMND", "5");
		}
	}
	
}
