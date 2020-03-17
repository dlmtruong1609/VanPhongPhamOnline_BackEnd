package com.nhom4.vanphongphamonline.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nhom4.vanphongphamonline.model.Customer;
import com.nhom4.vanphongphamonline.model.Account;
import com.nhom4.vanphongphamonline.repository.CustomerRepository;
@Component
public class AccountValidator implements Validator{ 
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Account.class.equals(clazz);
	}

	public void validateFormRegister(Object target, Errors errors) {
		// TODO Auto-generated method stub
		try {
			Account account = (Account) target;
			// ko đc để trống các nội dung sau:
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Email không được bỏ trống", "6");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Tài khoản không được bỏ trống", "8");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Mật khẩu không được để trống", "9");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "Mật khẩu xác nhận không được để trống", "10");
			// regex email
			String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
			Matcher matcher = null;
			Pattern pattern;
			if(account.getEmail() != null) {
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(account.getEmail());
				if(matcher.matches()!=true) {
					errors.rejectValue("email", "Email không đúng định dạng", "5");
				}
				if(customerRepository.findByAccount_Email(account.getEmail()) != null) {
					errors.rejectValue("email", "Email này đã được đăng ký", "7");
				}
			}
			
			if(account.getUsername() != null) {
				if (account.getUsername().length() < 6 || account.getUsername().length() > 32) {
		            errors.rejectValue("username", "Tên tài khoản phải lớn hơn 6 hoặc bé hơn 32 kí tự", "1");
		        }
		        if (customerRepository.findByAccount_Username(account.getUsername()) != null) {
		            errors.rejectValue("username", "Tên tài khoản đã tồn tại", "2");
		        }
			}

	        if(account.getPassword()!=null && account.getPasswordConfirm()!=null) {
	            if (account.getPassword().length() < 8 || account.getPasswordConfirm().length() > 32) {
	                errors.rejectValue("password", "Mật khẩu phải lớn hơn 8 hoặc bé hơn 32 kí tự", "3");
	            }

	            if (!account.getPassword().equals(account.getPasswordConfirm())) {
	                errors.rejectValue("passwordConfirm", "Xác nhận mật khẩu không trùng khớp", "4");
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
			Account account = (Account) target;
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Tên tài khoản không được bỏ trống", "3");
			Customer khachHangExistWithUsername = new Customer();
	        if(account.getUsername()!=null) {
	        	khachHangExistWithUsername = customerRepository.findByAccount_Username(account.getUsername());
	            if (khachHangExistWithUsername == null) {
	                errors.rejectValue("username", "Sai tên tài khoản hoặc mật khẩu", "1");
	            }
	        }
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Mật khẩu không được để trống", "2");
	        if(khachHangExistWithUsername!=null) {
		        if(account.getPassword()!=null) {
		            if (!bCryptPasswordEncoder.matches(account.getPassword(), khachHangExistWithUsername.getAccount().getPassword())) {
		                errors.rejectValue("password", "Sai tên tài khoản hoặc mật khẩu", "1");
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
