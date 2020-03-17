package com.nhom4.vanphongphamonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.model.Category;
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
	@GetMapping(value = "/api/v1/category/list")
	public ResponseEntity<ServiceStatus> getAllCategory() {
		List<Category> list = categoryRepository.findAll();
		if(list == null) {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có category"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Danh sách category", list), HttpStatus.OK);
	}

}
