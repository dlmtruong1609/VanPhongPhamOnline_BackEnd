package com.nhom4.vanphongphamonline.validators;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nhom4.vanphongphamonline.models.Customer;
import com.nhom4.vanphongphamonline.utils.CustomResponse;
@Component
public class CustomerValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Customer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Customer customer = (Customer) target;
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Tên khách hàng không bỏ trống", "1");
		if(customer.getName() != null && (customer.getName().length() < 3 || customer.getName().length() > 50)) {
			errors.rejectValue("name", "Tên khách hàng không được dưới 3 kí tự và không lớn hơn 50 kí tự", "3");
		}
		
		if(customer.getPhone() != null && customer.getPhone() != null) {
			String regex = "^0[0-9]{9}$"; // 10 số và bắt đầu là 0
			if(customer.getPhone().matches(regex)!=true) {
				errors.rejectValue("phone", "Sai định dạng số điện thoại", "4");
			}
		}
		String regex = "^[0-9]{9}$|^[0-9]{11}$"; // cmnd có 9 hoặc 11 kí tự (9 cmnd, 11 là căn cước)
		if(customer.getIdentityCard() != null && customer.getIdentityCard().matches(regex)!=true) {
			errors.rejectValue("identityCard", "Sai định dạng CMND", "5");
		}
	}
	
}
