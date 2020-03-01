package com.nhom4.vanphongphamonline.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nhom4.vanphongphamonline.model.KhachHang;
import com.nhom4.vanphongphamonline.model.TaiKhoan;
import com.nhom4.vanphongphamonline.repository.KhachHangRepository;
import com.nhom4.vanphongphamonline.services.SecurityServiceImpl;
@Component
public class TaiKhoanValidator implements Validator{ 
	@Autowired
	private KhachHangRepository khachHangRepository;
	@Autowired
	SecurityServiceImpl securityServiceImpl;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return TaiKhoan.class.equals(clazz);
	}

	public void validateFormRegister(Object target, Errors errors) {
		// TODO Auto-generated method stub
		try {
			TaiKhoan taiKhoan = (TaiKhoan) target;
			// ko đc để trống các nội dung sau:
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Email không được bỏ trống", "6");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taiKhoan", "Tài khoản không được bỏ trống", "8");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matKhau", "Mật khẩu không được để trống", "9");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matKhauXacNhan", "Mật khẩu xác nhận không được để trống", "10");
			// regex email
			String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
			Matcher matcher = null;
			Pattern pattern;
			if(taiKhoan.getEmail() != null) {
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(taiKhoan.getEmail());
				if(matcher.matches()!=true) {
					errors.rejectValue("email", "Email không đúng định dạng", "5");
				}
				if(khachHangRepository.findByEmail(taiKhoan.getEmail()) != null) {
					errors.rejectValue("email", "Email này đã được đăng ký", "7");
				}
			}
			
			if(taiKhoan.getTaiKhoan() != null) {
				if (taiKhoan.getTaiKhoan().length() < 6 || taiKhoan.getTaiKhoan().length() > 32) {
		            errors.rejectValue("taiKhoan", "Tên tài khoản phải lớn hơn 6 hoặc bé hơn 32 kí tự", "1");
		        }
		        if (khachHangRepository.findByUsername(taiKhoan.getTaiKhoan()) != null) {
		            errors.rejectValue("taiKhoan", "Tên tài khoản đã tồn tại", "2");
		        }
			}

	        if(taiKhoan.getMatKhau()!=null && taiKhoan.getMatKhauXacNhan()!=null) {
	            if (taiKhoan.getMatKhau().length() < 8 || taiKhoan.getMatKhau().length() > 32) {
	                errors.rejectValue("matKhau", "Mật khẩu phải lớn hơn 8 hoặc bé hơn 32 kí tự", "3");
	            }

	            if (!taiKhoan.getMatKhau().equals(taiKhoan.getMatKhauXacNhan())) {
	                errors.rejectValue("matKhauXacNhan", "Xác nhận mật khẩu không trùng khớp", "4");
	            }
	        }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void validateFormLogin(Object target, Errors errors) {
		// TODO Auto-generated method stub
		try {
			TaiKhoan taiKhoan = (TaiKhoan) target;
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taiKhoan", "Tên tài khoản không được bỏ trống", "3");
			KhachHang khachHangExistWithUsername = new KhachHang();
	        if(taiKhoan.getTaiKhoan()!=null) {
	        	khachHangExistWithUsername = khachHangRepository.findByUsername(taiKhoan.getTaiKhoan());
	            if (khachHangExistWithUsername == null) {
	                errors.rejectValue("taiKhoan", "Sai tên tài khoản hoặc mật khẩu", "1");
	            }
	        }
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matKhau", "Mật khẩu không được để trống", "2");
	        if(khachHangExistWithUsername!=null) {
		        if(taiKhoan.getMatKhau()!=null) {
		            if (!bCryptPasswordEncoder.matches(taiKhoan.getMatKhau(), khachHangExistWithUsername.getTaiKhoan().getMatKhau())) {
		                errors.rejectValue("matKhau", "Sai tên tài khoản hoặc mật khẩu", "1");
		            }
		        }
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
