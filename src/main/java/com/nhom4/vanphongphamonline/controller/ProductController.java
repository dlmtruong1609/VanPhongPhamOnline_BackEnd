package com.nhom4.vanphongphamonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

import com.nhom4.vanphongphamonline.model.Product;
import com.nhom4.vanphongphamonline.repository.ProductRepository;
import com.nhom4.vanphongphamonline.services.ServiceStatus;
import com.nhom4.vanphongphamonline.validator.ProductValidator;
@Controller
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired 
	private ProductValidator productValidator;
	@Autowired
	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	@ResponseBody
	@PostMapping(value = "/api/v1/admin/product/add")
	public ResponseEntity<ServiceStatus> addProduct(@RequestBody Product product, BindingResult bindingResult) {
		// check --------------------------------------
		productValidator.productValidation(product, bindingResult);
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
		//--------------------------------------------------------
		productRepository.insert(product);
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thêm sản phẩm thành công"), HttpStatus.OK);
		
	}
	@ResponseBody
	@GetMapping(value = "/api/v1/product/list")
	public ResponseEntity<ServiceStatus> getAllProduct() {
		List<Product> list = null;
		list = productRepository.findAll();
		if(list == null) {
			return new ResponseEntity<ServiceStatus>( new ServiceStatus(1, "Không có sản phẩm nào tồn tại"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>( new ServiceStatus(0, "Danh sách sản phẩm", list), HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value = "/api/v1/product/detail")
	public ResponseEntity<ServiceStatus> getProductById(@RequestParam String id) {
		if(productRepository.findById(id) == null) {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Sản phẩm không tìm thấy"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Tìm thành công", productRepository.findById(id)), HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "/api/v1/admin/product/delete")
	public ResponseEntity<ServiceStatus> deleteProductById(@RequestParam String id) {
		if(productRepository.findById(id).isPresent()!=false) {
			productRepository.deleteById(id);
		} else {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Sản phẩm không tồn tại"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Xoá sản phẩm thành công", productRepository.findAll()), HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "/api/v1/admin/product/update")
	public ResponseEntity<ServiceStatus> updateProductById(@RequestParam String id, @RequestBody Product product, BindingResult bindingResult) {
		if(productRepository.findById(id).isPresent()!=false) {
			// check ---------------------------------
			productValidator.productValidation(product, bindingResult);
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
			// ----------------------------------------------
			Product productUpdated = productRepository.findById(id).get();
			productUpdated.setName(product.getName());
			productUpdated.setPrice(product.getPrice());
			productUpdated.setDescription(product.getDescription());
			productUpdated.setInventory(product.getInventory());
			productRepository.save(productUpdated);
		} else {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Sản phẩm không tồn tại"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Cập nhật sản phẩm thành công sản phẩm thành công"), HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/api/v1/product/page") // phân trang
	public ResponseEntity<ServiceStatus> getProductPageByIndex(@RequestParam int index) {
		Page<Product> page = productRepository.findAll(PageRequest.of(index, 12)); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có sản phẩm"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Trang " + index, page), HttpStatus.OK);
	}
	//	Tim kiem text search, tạo index trước
	@ResponseBody //// db.product.ensureIndex({ name: "text", description : "text", category : "text" });
	@GetMapping(value = "/api/v1/product/search") // seacrh có phân trang
	public ResponseEntity<ServiceStatus> search(@RequestParam int index, @RequestParam String keyword) {
		Page<Product> page = productRepository.findByTextSearch(keyword, PageRequest.of(index, 12)); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có sản phẩm"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Trang " + index, page), HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/api/v1/product/asc") // sắp xếp có phân trang tăng dần hoặc a - z
	public ResponseEntity<ServiceStatus> sortFromAToZ(@RequestParam int index, @RequestParam String fieldSort) {
		Page<Product> page = productRepository.findAll(PageRequest.of(index, 12, Sort.by(Sort.Direction.ASC, fieldSort))); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có sản phẩm"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Trang " + index, page), HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/api/v1/product/desc") // sắp xếp có phân trang giảm dần hoặc z-a
	public ResponseEntity<ServiceStatus> sortFromZToA(@RequestParam int index, @RequestParam String fieldSort) {
		Page<Product> page = productRepository.findAll(PageRequest.of(index, 12, Sort.by(Sort.Direction.DESC, fieldSort))); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có sản phẩm"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Trang " + index, page), HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/api/v1/product/category") // sắp xếp có phân trang giảm dần hoặc z-a
	public ResponseEntity<ServiceStatus> searchByCategory(@RequestParam int index, @RequestParam String id) {
		Page<Product> page = productRepository.findByCategory_Id(PageRequest.of(index, 12), id); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có sản phẩm"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Trang " + index, page), HttpStatus.OK);
	}
	
}
