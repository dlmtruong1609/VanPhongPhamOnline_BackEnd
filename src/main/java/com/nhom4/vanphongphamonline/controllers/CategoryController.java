package com.nhom4.vanphongphamonline.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.nhom4.vanphongphamonline.models.Category;
import com.nhom4.vanphongphamonline.models.Product;
import com.nhom4.vanphongphamonline.models.Supplier;
import com.nhom4.vanphongphamonline.repositories.CategoryRepository;
import com.nhom4.vanphongphamonline.repositories.OrderRepository;
import com.nhom4.vanphongphamonline.utils.CustomResponse;

@RestController
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	@PostMapping(value = "/api/v1/admin/category/add")
	public ResponseEntity<CustomResponse> addCategory(@RequestBody Category category) { // chưa bắt valid
		categoryRepository.insert(category);
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Thêm loại sản phẩm thành công", null), HttpStatus.OK);
		
	}

	@PostMapping(value = "/api/v1/admin/category/delete")
	public ResponseEntity<CustomResponse> deleteCategoryById(@RequestParam String id) {
		if(categoryRepository.findById(id).isPresent()!=false) {
			categoryRepository.deleteById(id);
		} else {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Loại sản phẩm không tồn tại", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Xoá loại sản phẩm thành công", categoryRepository.findAll()), HttpStatus.OK);
	}
	@PostMapping(value = "/api/v1/admin/category/update")
	public ResponseEntity<CustomResponse> updateCategoryById(@RequestParam String id, @RequestBody Category category) { // chưa kiểm tra valid
		if(categoryRepository.findById(id).isPresent()!=false) {
			Category categoryUpdated = categoryRepository.findById(id).get();
			categoryUpdated.setName(category.getName());
			categoryRepository.save(categoryUpdated);
		} else {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Loại sản phẩm không tồn tại", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Cập nhật loại sản phẩm thành công sản phẩm thành công", null), HttpStatus.OK);
	}
	
	@GetMapping(value = "/api/v1/category/page") // phân trang
	public ResponseEntity<CustomResponse> getCategoryPageByIndex(@RequestParam int index) {
		Page<Category> page = categoryRepository.findAll(PageRequest.of(index, 12)); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Không có loại sản phẩm nào", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Trang " + index, page), HttpStatus.OK);
	}
	@GetMapping(value = "/api/v1/category/detail")
	public ResponseEntity<CustomResponse> getCategoryById(@RequestParam String id) {
		if(categoryRepository.findById(id) == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Sản phẩm không tìm thấy", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Tìm thành công", categoryRepository.findById(id)), HttpStatus.OK);
	}
	//	Tim kiem text search, tạo index trước
	//// db.category.ensureIndex({ name: "text"});
	@GetMapping(value = "/api/v1/category/search") // seacrh có phân trang
	public ResponseEntity<CustomResponse> search(@RequestParam int index, @RequestParam String keyword) {
		Page<Category> page = categoryRepository.findByTextSearch(keyword, PageRequest.of(index, 12)); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Không có loại sản phẩm", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Trang " + index, page), HttpStatus.OK);
	}
	@GetMapping(value = "/api/v1/category/list")
	public ResponseEntity<CustomResponse> getAllCategory() {
		List<Category> list = categoryRepository.findAll();
		if(list == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Không có category", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Danh sách category", list), HttpStatus.OK);
	}

}
