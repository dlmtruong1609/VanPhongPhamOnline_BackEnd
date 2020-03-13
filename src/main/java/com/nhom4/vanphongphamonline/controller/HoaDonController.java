package com.nhom4.vanphongphamonline.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.model.HoaDon;
import com.nhom4.vanphongphamonline.model.KhachHang;
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
	@PostMapping(value = "/api/hoadon/thanhtoan") // sử dụng khi thanh toán ko dùng để add vào giỏ hàng
	public ResponseEntity<ServiceStatus> createOrder(@RequestBody HoaDon hoaDon, BindingResult bindingResult, HttpServletRequest request) throws ParseException {
		// check ---------------------------------------
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
		//--------------------------------------------------
	      
		hoaDon.setNgayLapHoaDon(new Date());
		hoaDonRepository.insert(hoaDon);
		HttpSession session = request.getSession();
		session.invalidate();
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thanh toán thành công"), HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value = "/api/hoadon/chitiet")
	public ResponseEntity<ServiceStatus> getOrderById(@RequestParam String id, @RequestParam String username) {
		Optional<HoaDon> hoaDon = null;
		if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username)) {
			hoaDon = hoaDonRepository.findById(id);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Chi tiết hoá đơn", hoaDon), HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/api/hoadon/danhsach")
	public ResponseEntity<ServiceStatus> getAllOrder(@RequestParam String username) {
		List<HoaDon> list = null;
		if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username)) {
			list = hoaDonRepository.getAllOrderByUsername(username);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Danh sách hoá đơn", list), HttpStatus.OK);
	}
}
