package com.nhom4.vanphongphamonline.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nhom4.vanphongphamonline.models.Category;
import com.nhom4.vanphongphamonline.models.Product;
import com.nhom4.vanphongphamonline.models.Supplier;
import com.nhom4.vanphongphamonline.repositories.SupplierRepository;
import com.nhom4.vanphongphamonline.utils.CustomResponse;

@RestController
public class SupplierController {
	@Autowired
	private SupplierRepository supplierRepository;
	@Autowired
	public SupplierController(SupplierRepository supplierRepository) {
		this.supplierRepository = supplierRepository;
		// TODO Auto-generated constructor stub
	}
	@PostMapping(value = "/api/v1/admin/supplier/add")
	public ResponseEntity<CustomResponse> addSupplier(@RequestBody Supplier supplier) { // chưa bắt valid
		supplierRepository.insert(supplier);
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Thêm nhà cung cấp thành công", null), HttpStatus.OK);
		
	}
	@GetMapping(value = "/api/v1/supplier/list")
	public ResponseEntity<CustomResponse> getAllSupplier() {
		List<Supplier> list = null;
		list = supplierRepository.findAll();
		if(list == null) {
			return new ResponseEntity<CustomResponse>( new CustomResponse(1, "Không có nhà cung cấp tồn tại", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>( new CustomResponse(0, "Danh sách nhà cung cấp", list), HttpStatus.OK);
	}
	
	@PostMapping(value = "/api/v1/admin/supplier/delete")
	public ResponseEntity<CustomResponse> deleteSupplierById(@RequestParam String id) {
		if(supplierRepository.findById(id).isPresent()!=false) {
			supplierRepository.deleteById(id);
		} else {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Nhà cung cấp không tồn tại", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Xoá nhà cung cấp thành công", supplierRepository.findAll()), HttpStatus.OK);
	}

	@PostMapping(value = "/api/v1/admin/supplier/update")
	public ResponseEntity<CustomResponse> updateProductById(@RequestParam String id, @RequestBody Supplier supplier) { // chưa kiểm tra valid
		if(supplierRepository.findById(id).isPresent()!=false) {
			Supplier supplierUpdated = supplierRepository.findById(id).get();
			supplierUpdated.setName(supplier.getName());
			supplierUpdated.setDescription(supplier.getDescription());

			supplierRepository.save(supplierUpdated);
		} else {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Nhà cung cấp không tồn tại", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Cập nhật nhà cung cấp thành công sản phẩm thành công", null), HttpStatus.OK);
	}

	@GetMapping(value = "/api/v1/supplier/detail")
	public ResponseEntity<CustomResponse> getSupplierById(@RequestParam String id) {
		if(supplierRepository.findById(id) == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Sản phẩm không tìm thấy", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Tìm thành công", supplierRepository.findById(id)), HttpStatus.OK);
	}

	@GetMapping(value = "/api/v1/supplier/page") // phân trang
	public ResponseEntity<CustomResponse> getSupplierPageByIndex(@RequestParam int index) {
		Page<Supplier> page = supplierRepository.findAll(PageRequest.of(index, 12)); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Không có nhà cung cấp nào", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Trang " + index, page), HttpStatus.OK);
	}
	
	//	Tim kiem text search, tạo index trước
	 //// db.supplier.ensureIndex({ name: "text", description: "text"});
	@GetMapping(value = "/api/v1/supplier/search") // seacrh có phân trang
	public ResponseEntity<CustomResponse> search(@RequestParam int index, @RequestParam String keyword) {
		Page<Supplier> page = supplierRepository.findByTextSearch(keyword, PageRequest.of(index, 12)); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Không có nhà cung cấp", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Trang " + index, page), HttpStatus.OK);
	}
}
