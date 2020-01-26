package com.nhom4.vanphongphamonline.validator;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nhom4.vanphongphamonline.model.TaiKhoan;
import com.nhom4.vanphongphamonline.repository.TaiKhoanRepository;
import com.nhom4.vanphongphamonline.services.SecurityServiceImpl;
@Component
public class TaiKhoanValidator implements Validator{ 
	@Autowired
	private TaiKhoanRepository taiKhoanRepository;
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
		TaiKhoan taiKhoan = (TaiKhoan) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Matcher matcher = null;
		Pattern pattern;
		if(taiKhoan.getEmail() != null) {
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(taiKhoan.getEmail());
			if(matcher.matches()!=true) {
				errors.rejectValue("email", "Email không đúng định dạng", "5");
			}
			if(taiKhoanRepository.findByEmail(taiKhoan.getEmail()) != null) {
				errors.rejectValue("email", "Email này đã được đăng ký", "7");
			}
		} else {
			errors.rejectValue("email", "Email không được bỏ trống", "6");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taiKhoan", "NotEmpty");
		if (taiKhoan.getTaiKhoan().length() < 6 || taiKhoan.getTaiKhoan().length() > 32) {
            errors.rejectValue("taiKhoan", "Tên tài khoản phải lớn hơn 6 hoặc bé hơn 32 kí tự", "1");
        }
        if (taiKhoanRepository.findByUsername(taiKhoan.getTaiKhoan()) != null) {
            errors.rejectValue("taiKhoan", "Tên tài khoản đã tồn tại", "2");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matKhau", "NotEmpty");
        if (taiKhoan.getMatKhau().length() < 8 || taiKhoan.getMatKhau().length() > 32) {
            errors.rejectValue("matKhau", "Mật khẩu phải lớn hơn 8 hoặc bé hơn 32 kí tự", "3");
        }

        if (!taiKhoan.getMatKhauXacNhan().equals(taiKhoan.getMatKhauXacNhan())) {
            errors.rejectValue("matKhauXacNhan", "Xác nhận mật khẩu không trùng khớp", "4");
        }
	}
	public void validateFormLogin(Object target, Errors errors) {
		// TODO Auto-generated method stub
		TaiKhoan taiKhoan = (TaiKhoan) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taiKhoan", "NotEmpty");
		if (taiKhoan.getTaiKhoan().length() < 6 || taiKhoan.getTaiKhoan().length() > 32) {
            errors.rejectValue("taiKhoan", "Tên tài khoản phải lớn hơn 6 hoặc bé hơn 32 kí tự", "1");
        }
        if (taiKhoanRepository.findByUsername(taiKhoan.getTaiKhoan()) == null) {
            errors.rejectValue("taiKhoan", "Sai tên tài khoản hoặc mật khẩu", "2");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matKhau", "NotEmpty");
        if (taiKhoan.getMatKhau().length() < 8 || taiKhoan.getMatKhau().length() > 32) {
            errors.rejectValue("matKhau", "Mật khẩu phải lớn hơn 8 hoặc bé hơn 32 kí tự", "3");
        }

        if (!bCryptPasswordEncoder.matches(taiKhoan.getMatKhau(), taiKhoanRepository.findByUsername(taiKhoan.getTaiKhoan()).getMatKhau())) {
            errors.rejectValue("matKhau", "Sai tên tài khoản hoặc mật khẩu", "4");
        }
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

	
}
