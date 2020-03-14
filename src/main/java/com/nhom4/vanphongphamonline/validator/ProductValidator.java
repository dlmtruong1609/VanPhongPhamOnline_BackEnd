package com.nhom4.vanphongphamonline.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nhom4.vanphongphamonline.model.Product;
import com.nhom4.vanphongphamonline.model.Account;
import com.nhom4.vanphongphamonline.repository.ProductRepository;
@Component
public class ProductValidator implements Validator{
	@Autowired
	ProductRepository productRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Account.class.equals(clazz);
	}
	
	public void productValidation(Object target, Errors errors) {
		try {
			Product product = (Product) target;
			// ko đc để trống các nội dung sau:
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Tên sản phẩm không bỏ trống", "1");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "Giá sản phẩm không bỏ trống", "2");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inventory", "Số lượng tồn không bỏ trống", "3");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "supplier", "Nhà cung cấp không bỏ trống", "4");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "caterory", "Loại sản phẩm không bỏ trống", "5");
			
			String regex = "^[0-9]";
			if(String.valueOf(product.getPrice()).matches(regex) == true) {
				errors.rejectValue("price", "Giá sản phẩm phải là số", "6");
			}
			if(String.valueOf(product.getInventory()).matches(regex) == true) {
				errors.rejectValue("inventory", "Số lượng tồn phải là số", "7");
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
