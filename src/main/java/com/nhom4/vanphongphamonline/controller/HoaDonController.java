package com.nhom4.vanphongphamonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.model.HoaDon;
import com.nhom4.vanphongphamonline.repository.ChiTietHoaDonRepository;
import com.nhom4.vanphongphamonline.repository.HoaDonRepository;
import com.nhom4.vanphongphamonline.services.ServiceStatus;
import com.nhom4.vanphongphamonline.validator.HoaDonValidator;

@Controller
public class HoaDonController {
	@Autowired
	private HoaDonRepository hoaDonRepository;
	@Autowired
	private ChiTietHoaDonRepository chiTietHoaDonRepository;
	@Autowired
	private HoaDonValidator hoaDonValidator;
	@Autowired
	public HoaDonController(HoaDonRepository hoaDonRepository) {
		this.hoaDonRepository = hoaDonRepository;
	}
	@ResponseBody
	@PostMapping(value = "/api/hoadon/thanhtoan")
	public ResponseEntity<ServiceStatus> createOrder(@RequestBody HoaDon hoaDon, BindingResult bindingResult) {
		hoaDonValidator.validate(hoaDon, bindingResult);
		if (bindingResult.hasErrors()) {
			   FieldError fieldError = null;
			   for (Object object : bindingResult.getAllErrors()) {
				    if(object instanceof FieldError) {
				        fieldError = (FieldError) object;
				    }
				}
				   ServiceStatus serviceStatusError = new ServiceStatus(Integer.parseInt(fieldError.getDefaultMessage()), String.valueOf(fieldError.getCode()));
			   
			   return new ResponseEntity<ServiceStatus>(serviceStatusError, HttpStatus.OK);
	        }
		hoaDon.getDanhsachCTHD().forEach(item -> {
			chiTietHoaDonRepository.insert(item);
		});
		hoaDonRepository.insert(hoaDon);
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thanh toán thành công"), HttpStatus.OK);
	}
	
}
