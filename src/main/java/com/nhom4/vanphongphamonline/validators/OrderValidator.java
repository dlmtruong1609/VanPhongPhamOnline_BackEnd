package com.nhom4.vanphongphamonline.validators;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nhom4.vanphongphamonline.models.Account;
import com.nhom4.vanphongphamonline.models.Order;
import com.nhom4.vanphongphamonline.models.OrderDetail;
import com.nhom4.vanphongphamonline.utils.CustomResponse;
@Component
public class OrderValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Account.class.equals(clazz);
	}

	public void validateOrderDetail(Object target, Errors errors) {
		// TODO Auto-generated method stub
		try {
			OrderDetail orderDetail = (OrderDetail) target;
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tongTien", "Chưa thành tiền", "1");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ngayLapHoaDon", "Chưa có ngày tạo", "2");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "khachHang", "Hoá đơn chưa có khách hàng", "3");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "danhsachCTHD", "Chưa có sản phẩm cần thanh toán", "4");
//			
			int inventory = orderDetail.getProduct().getInventory();
			int quantityOrder = orderDetail.getQuantity();
			if(quantityOrder <= 0) {
				errors.rejectValue("listOrderDetail", "Số lượng phải lớn hơn hoặc bằng 1", "1");
			} else if(quantityOrder > inventory) {
				errors.rejectValue("listOrderDetail",  "Số lượng phải nhỏ hơn hoặc bằng số của sản phẩm là " + inventory, "2");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Order hoaDon = (Order) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "totalMoney", "Chưa thành tiền", "1");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer", "Hoá đơn chưa có khách hàng", "2");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "listOrderDetail", "Chưa có sản phẩm cần thanh toán", "3");
	}

}
