package com.nhom4.vanphongphamonline.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nhom4.vanphongphamonline.models.Product;
import com.nhom4.vanphongphamonline.repositories.ProductRepository;
import com.nhom4.vanphongphamonline.utils.CustomResponse;
import com.nhom4.vanphongphamonline.validators.ProductValidator;
@RestController
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductValidator productValidator;
	@Autowired
	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@PostMapping(value = "/api/v1/admin/product/add")
	public ResponseEntity<CustomResponse> addProduct(@RequestBody Product product, BindingResult bindingResult) {
		// check --------------------------------------
		productValidator.productValidation(product, bindingResult);
		if (bindingResult.hasErrors()) {
			   FieldError fieldError = null;
			   for (Object object : bindingResult.getAllErrors()) {
				    if(object instanceof FieldError) {
				        fieldError = (FieldError) object;
				    }
				}
				   CustomResponse serviceStatusError = new CustomResponse(Integer.parseInt(fieldError.getDefaultMessage()), String.valueOf(fieldError.getCode()), null);
			   
			   return new ResponseEntity<CustomResponse>(serviceStatusError, HttpStatus.OK);
	        }
		//--------------------------------------------------------
		productRepository.insert(product);
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Thêm sản phẩm thành công", null), HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/api/v1/product/list")
	public ResponseEntity<CustomResponse> getAllProduct() {
		List<Product> list = null;
		list = productRepository.findAll();
		if(list == null) {
			return new ResponseEntity<CustomResponse>( new CustomResponse(1, "Không có sản phẩm nào tồn tại", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>( new CustomResponse(0, "Danh sách sản phẩm", list), HttpStatus.OK);
	}
	@GetMapping(value = "/admin/product")
	public ModelAndView index(Model model, @RequestParam String index) {
		Page<Product> page = productRepository.findAll(PageRequest.of(Integer.parseInt(index), 12));
		model.addAttribute("listProduct", page.getContent());
		return new ModelAndView("ProductAdmin");
	}
	@GetMapping(value = "/api/v1/product/detail")
	public ResponseEntity<CustomResponse> getProductById(@RequestParam String id) {
		if(productRepository.findById(id) == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Sản phẩm không tìm thấy", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Tìm thành công", productRepository.findById(id)), HttpStatus.OK);
	}
	
	@PostMapping(value = "/api/v1/admin/product/delete")
	public String deleteProductById(@RequestParam String id, Model model) {
		if(productRepository.findById(id).isPresent()!=false) {
			productRepository.deleteById(id);
		}
		Page<Product> page = productRepository.findAll(PageRequest.of(0, 12));
		model.addAttribute("listProduct", page.getContent());
		return "redirect:/admin?index=0";
	}

	@PostMapping(value = "/api/v1/admin/product/update")
	public ResponseEntity<CustomResponse> updateProductById(@RequestParam String id, @RequestBody Product product, BindingResult bindingResult) {
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
				   CustomResponse serviceStatusError = new CustomResponse(Integer.parseInt(fieldError.getDefaultMessage()), String.valueOf(fieldError.getCode()), null);
			   
			   return new ResponseEntity<CustomResponse>(serviceStatusError, HttpStatus.OK);
	        } 
			// ----------------------------------------------
			Product productUpdated = productRepository.findById(id).get();
			productUpdated.setName(product.getName());
			productUpdated.setPrice(product.getPrice());
			productUpdated.setDescription(product.getDescription());
			productUpdated.setInventory(product.getInventory());
			productUpdated.setSupplier(product.getSupplier());
			productUpdated.setUrlImage(product.getUrlImage());
			productUpdated.setCategory(product.getCategory());
			productRepository.save(productUpdated);
		} else {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Sản phẩm không tồn tại", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Cập nhật sản phẩm thành công sản phẩm thành công", null), HttpStatus.OK);
	}
	
	@GetMapping(value = "/api/v1/product/page") // phân trang
	public ResponseEntity<CustomResponse> getProductPageByIndex(@RequestParam int index) {
		Page<Product> page = productRepository.findAll(PageRequest.of(index, 12)); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Không có sản phẩm", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Trang " + index, page), HttpStatus.OK);
	}
	//	Tim kiem text search, tạo index trước
	//// db.products.ensureIndex({ name: "text", description : "text", category : "text" });
	@GetMapping(value = "/api/v1/product/search") // seacrh có phân trang
	public ResponseEntity<CustomResponse> search(@RequestParam int index, @RequestParam String keyword) {
		Page<Product> page = productRepository.findByTextSearch(keyword, PageRequest.of(index, 12)); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Không có sản phẩm", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Trang " + index, page), HttpStatus.OK);
	}
	@GetMapping(value = "/api/v1/admin/product/search")
	public ModelAndView adminSearch(@RequestParam String keyword, Model model) {
		List<Product> list = productRepository.findByTextSearch(keyword);
		System.out.println(list);
		model.addAttribute("listProduct",list);
		return new ModelAndView("ProductAdmin");
	}
	@GetMapping(value = "/api/v1/product/asc") // sắp xếp có phân trang tăng dần hoặc a - z
	public ResponseEntity<CustomResponse> sortFromAToZ(@RequestParam int index, @RequestParam String fieldSort) {
		Page<Product> page = productRepository.findAll(PageRequest.of(index, 12, Sort.by(Sort.Direction.ASC, fieldSort))); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Không có sản phẩm", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Trang " + index, page), HttpStatus.OK);
	}
	
	@GetMapping(value = "/api/v1/product/desc") // sắp xếp có phân trang giảm dần hoặc z-a
	public ResponseEntity<CustomResponse> sortFromZToA(@RequestParam int index, @RequestParam String fieldSort) {
		Page<Product> page = productRepository.findAll(PageRequest.of(index, 12, Sort.by(Sort.Direction.DESC, fieldSort))); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Không có sản phẩm", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Trang " + index, page), HttpStatus.OK);
	}
	
	@GetMapping(value = "/api/v1/product/category") // sắp xếp có phân trang giảm dần hoặc z-a
	public ResponseEntity<CustomResponse> searchByCategory(@RequestParam int index, @RequestParam String name) {
		Page<Product> page = productRepository.findByCategory_Name(PageRequest.of(index, 12), name); // 1 page có 12 sản phẩm
		if(page == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Không có sản phẩm", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Trang " + index, page), HttpStatus.OK);
	}
	
}
