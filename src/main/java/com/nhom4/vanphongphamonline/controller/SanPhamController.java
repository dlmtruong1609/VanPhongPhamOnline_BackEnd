package com.nhom4.vanphongphamonline.controller;

import java.awt.PageAttributes.MediaType;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.TextCriteria;
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

import com.nhom4.vanphongphamonline.model.SanPham;
import com.nhom4.vanphongphamonline.repository.SanPhamRepository;
import com.nhom4.vanphongphamonline.services.ServiceStatus;
import com.nhom4.vanphongphamonline.validator.SanPhamValidator;
@Controller
public class SanPhamController {
	@Autowired
	SanPhamRepository sanPhamRepository;
	@Autowired 
	SanPhamValidator sanPhamValidator;
	@Autowired
	public SanPhamController(SanPhamRepository sanPhamRepository) {
		this.sanPhamRepository = sanPhamRepository;
	}
	@ResponseBody
	@PostMapping(value = "/quanly/sanpham/them")
	public ResponseEntity<ServiceStatus> addProduct(@RequestBody SanPham sanPham, BindingResult bindingResult) {
		sanPhamValidator.productValidation(sanPham, bindingResult);
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
		sanPhamRepository.insert(sanPham);
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thêm sản phẩm thành công"), HttpStatus.OK);
		
	}
	@ResponseBody
	@GetMapping(value = "quanly/sanpham/danhsach")
	public ResponseEntity<List<SanPham>> getAllProduct() {
		List<SanPham> list = null;
		list = sanPhamRepository.findAll();
		return new ResponseEntity<List<SanPham>>(list, HttpStatus.OK);
	}
//	Tim kiem text search
	@ResponseBody
	@GetMapping(value = "quanly/sanpham/timkiem")
	public ResponseEntity<List<SanPham>> getAllProductByName(@RequestParam String name) {
		List<SanPham> list = null;
		list = sanPhamRepository.findByProductName(name);
		return new ResponseEntity<List<SanPham>>(list, HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value = "quanly/sanpham/chitiet")
	public Optional<SanPham> getProductById(@RequestParam String id) {
		return sanPhamRepository.findById(id);
	}
	@ResponseBody
	@PostMapping(value = "quanly/sanpham/xoa")
	public ResponseEntity<ServiceStatus> deleteProductById(@RequestParam String id) {
		if(sanPhamRepository.findById(id).isPresent()!=false) {
			sanPhamRepository.deleteById(id);
		} else {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Sản phẩm không tồn tại"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Xoá sản phẩm thành công"), HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "quanly/sanpham/capnhat")
	public ResponseEntity<ServiceStatus> updateProductById(@RequestParam String id, @RequestBody SanPham sanPham, BindingResult bindingResult) {
		if(sanPhamRepository.findById(id).isPresent()!=false) {
			sanPhamValidator.productValidation(sanPham, bindingResult);
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
			SanPham sp = sanPhamRepository.findById(id).get();
			sp.setTenSanPham(sanPham.getTenSanPham());
			sp.setGiaSanPham(sanPham.getGiaSanPham());
			sp.setMoTa(sanPham.getMoTa());
			sp.setSoLuongTon(sanPham.getSoLuongTon());
			sanPhamRepository.save(sp);
		} else {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Sản phẩm không tồn tại"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Cập nhật sản phẩm thành công sản phẩm thành công"), HttpStatus.OK);
	}
	
}
