package com.nhom4.vanphongphamonline.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nhom4.vanphongphamonline.model.SanPham;
import com.nhom4.vanphongphamonline.model.TaiKhoan;
import com.nhom4.vanphongphamonline.repository.SanPhamRepository;
@Component
public class SanPhamValidator implements Validator{
	@Autowired
	SanPhamRepository sanPhamRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return TaiKhoan.class.equals(clazz);
	}
	
	public void productValidation(Object target, Errors errors) {
		try {
			SanPham sanPham = (SanPham) target;
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenSanPham", "Tên sản phẩm không bỏ trống", "1");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "giaSanPham", "Giá sản phẩm không bỏ trống", "2");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "soLuongTon", "Số lượng tồn không bỏ trống", "3");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nhaCungCap", "Nhà cung cấp không bỏ trống", "4");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loaiSanPham", "Loại sản phẩm không bỏ trống", "5");
			
			String regex = "^[0-9]";
			if(String.valueOf(sanPham.getGiaSanPham()).matches(regex) == true) {
				errors.rejectValue("giaSanPham", "Giá sản phẩm phải là số", "6");
			}
			if(String.valueOf(sanPham.getSoLuongTon()).matches(regex) == true) {
				errors.rejectValue("soLuongTon", "Số lượng tồn phải là số", "7");
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