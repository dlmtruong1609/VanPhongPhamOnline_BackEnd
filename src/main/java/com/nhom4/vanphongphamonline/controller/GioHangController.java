package com.nhom4.vanphongphamonline.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.WebSession;

import com.nhom4.vanphongphamonline.model.ChiTietHoaDon;
import com.nhom4.vanphongphamonline.model.HoaDon;
import com.nhom4.vanphongphamonline.model.SanPham;
import com.nhom4.vanphongphamonline.services.ServiceStatus;
import com.nhom4.vanphongphamonline.validator.HoaDonValidator;

@Controller
public class GioHangController {
	@Autowired
	private static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	List<ChiTietHoaDon> list;
	@Autowired
	HoaDonValidator hoaDonValidator;
	@ResponseBody
	@PostMapping(value = "/api/giohang/them")
	public ResponseEntity<ServiceStatus> saveOrder(HttpServletRequest request, @RequestBody ChiTietHoaDon chiTietHoaDon, BindingResult bindingResult) {
		HttpSession session = request.getSession();
		HoaDon hdSS = (HoaDon) session.getAttribute("hoaDon");
		// check		
		hoaDonValidator.validate(chiTietHoaDon, bindingResult);
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
		list = new ArrayList<ChiTietHoaDon>();
		list.add(chiTietHoaDon);
		if(hdSS != null) {
			hdSS.getDanhsachCTHD().forEach(item -> {
				if(item.getSanPham().getMaSanPham().equals(chiTietHoaDon.getSanPham().getMaSanPham())) {
					item.setSoLuong(item.getSoLuong() + chiTietHoaDon.getSoLuong());
					item.setDonGia(item.getDonGia() + chiTietHoaDon.getDonGia());
					list.remove(chiTietHoaDon);
				}
			});
			hdSS.getDanhsachCTHD().addAll(list);
			hdSS.setDanhsachCTHD(hdSS.getDanhsachCTHD());
		} else {
			hdSS = new HoaDon();
			hdSS.setDanhsachCTHD(list);;
		}
		session.setAttribute("hoaDon", hdSS);
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thêm thành con vào giỏ hàng"), HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "/api/giohang/capnhat") // id cua sanpham, action la tang hoac giam
	public ResponseEntity<ServiceStatus> updateOrder(@RequestBody HoaDon hoaDon, HttpServletRequest request, BindingResult bindingResult) {
		HttpSession session = request.getSession();
		for (int i = 0; i < hoaDon.getDanhsachCTHD().size(); i++) {
			ChiTietHoaDon chiTietHoaDon = hoaDon.getDanhsachCTHD().get(i);
			hoaDonValidator.validate(chiTietHoaDon, bindingResult);
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
		}
		session.setAttribute("hoaDon", hoaDon);
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Cập nhật thành công", session.getAttribute("hoaDon")), HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/api/giohang/dulieu")
	public ResponseEntity<ServiceStatus> getOrderInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		request.getHeader("cookie");
		if(session.getAttribute("hoaDon") == null) {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có sản phẩm trong giỏ hàng", ""), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thành công", session.getAttribute("hoaDon")), HttpStatus.OK);
	}
	int i = 0;
	@ResponseBody
	@PostMapping(value = "/api/giohang/xoa") // id la ma san pham
	public ResponseEntity<ServiceStatus> removeOrder(@RequestParam String id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		HoaDon hd = (HoaDon) session.getAttribute("hoaDon");
		if(hd != null) {
			hd.getDanhsachCTHD().forEach((item) -> {
				if(item.getSanPham().getMaSanPham().equals(id)) {
					hd.getDanhsachCTHD().remove(i);
				}
				i++;
			});;
			i = 0;
		} else {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có sản phẩm trong giỏ hàng", ""), HttpStatus.OK);
		}
		session.setAttribute("hoaDon", hd);
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thành công", ""), HttpStatus.OK);
	}
}
