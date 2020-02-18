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

@Controller
public class GioHangController {
	@Autowired
	private static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	@ResponseBody
	@PostMapping(value = "/api/giohang/them")
	public ResponseEntity<ServiceStatus> saveOrder(HttpServletRequest request, @RequestBody ChiTietHoaDon chiTietHoaDon) {
		HttpSession session = request.getSession();
		HoaDon hdSS = (HoaDon) session.getAttribute("hoaDon");
		chiTietHoaDon.getSanPham().setMaSanPham(String.valueOf(ID_GENERATOR.getAndIncrement()));
		List<ChiTietHoaDon> list = new ArrayList<ChiTietHoaDon>();
		list.add(chiTietHoaDon);
		if(hdSS != null) {
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
	public ResponseEntity<ServiceStatus> updateOrder(@RequestParam String action, @RequestParam String id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		HoaDon hd = (HoaDon) session.getAttribute("hoaDon");
		if(hd != null) {
			if(action.equals("tang")) {
				hd.getDanhsachCTHD().forEach((item) -> {
					if(item.getSanPham().getMaSanPham().equals(id)) {
						item.setSoLuong(item.getSoLuong() + 1);
					}
				});;
			} else if (action.equals("giam")) {
				hd.getDanhsachCTHD().forEach((item) -> {
					if(item.getSanPham().getMaSanPham().equals(id)) {
						item.setSoLuong(item.getSoLuong() - 1);
					}
				});;
			}	
		} else {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có sản phẩm trong giỏ hàng", ""), HttpStatus.OK);
		}
		session.setAttribute("hoaDon", hd);
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thành công", ""), HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value = "/api/giohang/dulieu")
	public ResponseEntity<ServiceStatus> getOrderInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("hoaDon") == null) {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có sản phẩm trong giỏ hàng", ""), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thành công", session.getAttribute("hoaDon").toString()), HttpStatus.OK);
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
