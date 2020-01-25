package com.nhom4.vanphongphamonline.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nhom4.vanphongphamonline.model.TaiKhoan;
import com.nhom4.vanphongphamonline.repository.TaiKhoanRepository;
@Component
public class TaiKhoanValidator implements Validator{ 
	@Autowired
	private TaiKhoanRepository taiKhoanRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return TaiKhoan.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		TaiKhoan taiKhoan = (TaiKhoan) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taiKhoan", "NotEmpty");
		if (taiKhoan.getTaiKhoan().length() < 6 || taiKhoan.getTaiKhoan().length() > 32) {
            errors.rejectValue("taiKhoan", "Size.userForm.username");
        }
        if (taiKhoanRepository.findByUsername(taiKhoan.getTaiKhoan()) != null) {
            errors.rejectValue("taiKhoan", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matKhau", "NotEmpty");
        if (taiKhoan.getMatKhau().length() < 8 || taiKhoan.getMatKhau().length() > 32) {
            errors.rejectValue("matKhau", "Size.userForm.password");
        }

        if (!taiKhoan.getMatKhauXacNhan().equals(taiKhoan.getMatKhauXacNhan())) {
            errors.rejectValue("matKhauXacNhan", "Diff.userForm.passwordConfirm");
        }
	}

	
}
