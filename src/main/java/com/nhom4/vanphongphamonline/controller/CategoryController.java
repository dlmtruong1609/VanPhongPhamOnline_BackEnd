package com.nhom4.vanphongphamonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.model.Category;
import com.nhom4.vanphongphamonline.model.Product;
import com.nhom4.vanphongphamonline.model.Supplier;
import com.nhom4.vanphongphamonline.repository.CategoryRepository;
import com.nhom4.vanphongphamonline.repository.OrderRepository;
import com.nhom4.vanphongphamonline.services.ServiceStatus;

@Controller
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	@ResponseBody
	@PostMapping(value = "/api/v1/admin/category/add")
	public ResponseEntity<ServiceStatus> addCategory(@RequestBody Category category) { // chưa bắt valid
		categoryRepository.insert(category);
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thêm loại sản phẩm thành công"), HttpStatus.OK);
		
	}

	@ResponseBody
	@PostMapping(value = "/api/v1/admin/category/delete")
	public ResponseEntity<ServiceStatus> deleteCategoryById(@RequestParam String id) {
		if(categoryRepository.findById(id).isPresent()!=false) {
			categoryRepository.deleteById(id);
		} else {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Loại sản phẩm không tồn tại"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Xoá loại sản phẩm thành công", categoryRepository.findAll()), HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "/api/v1/admin/category/update")
	public ResponseEntity<ServiceStatus> updateCategoryById(@RequestParam String id, @RequestBody Category category) { // chưa kiểm tra valid
		if(categoryRepository.findById(id).isPresent()!=false) {
			Category categoryUpdated = categoryRepository.findById(id).get();
			categoryUpdated.setName(category.getName());
			categoryRepository.save(categoryUpdated);
		} else {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Loại sản phẩm không tồn tại"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Cập nhật loại sản phẩm thành công sản phẩm thành công"), HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/api/v1/category/page") // phân trang
	public ResponseEntity<ServiceStatus> getCategoryPageByIndex(@RequestParam int index) {
		Page<Category> page = categoryRepository.findAll(PageRequest.of(index, 12)); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có loại sản phẩm nào"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Trang " + index, page), HttpStatus.OK);
	}
	
	//	Tim kiem text search, tạo index trước
	@ResponseBody //// db.category.ensureIndex({ name: "text"});
	@GetMapping(value = "/api/v1/category/search") // seacrh có phân trang
	public ResponseEntity<ServiceStatus> search(@RequestParam int index, @RequestParam String keyword) {
		Page<Category> page = categoryRepository.findByTextSearch(keyword, PageRequest.of(index, 12)); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có loại sản phẩm"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Trang " + index, page), HttpStatus.OK);
	}

}
