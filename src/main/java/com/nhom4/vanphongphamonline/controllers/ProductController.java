package com.nhom4.vanphongphamonline.controllers;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nhom4.vanphongphamonline.models.Category;
import com.nhom4.vanphongphamonline.models.FileData;
import com.nhom4.vanphongphamonline.models.Product;
import com.nhom4.vanphongphamonline.models.Supplier;
import com.nhom4.vanphongphamonline.repositories.CategoryRepository;
import com.nhom4.vanphongphamonline.repositories.ProductRepository;
import com.nhom4.vanphongphamonline.repositories.SupplierRepository;
import com.nhom4.vanphongphamonline.services.FileStorageService;
import com.nhom4.vanphongphamonline.utils.CustomResponse;
import com.nhom4.vanphongphamonline.validators.ProductValidator;
@RestController
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private SupplierRepository supplierRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductValidator productValidator;
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	public String uploadFileAdmin(MultipartFile file) throws IOException {
		FileData fileData = fileStorageService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/v1/file/")
				.path(fileData.getId())
				.toUriString();

		return fileDownloadUri + ".png";
	}
	@PostMapping(value = "/admin/product/add")
	public ModelAndView addProduct(@ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file, BindingResult bindingResult, HttpServletRequest req) throws IOException, ServletException {
		// check --------------------------------------
		productValidator.productValidation(product, bindingResult);
		String message = "Thêm thành công";
		if (bindingResult.hasErrors()) {
			FieldError fieldError = null;
			for (Object object : bindingResult.getAllErrors()) {
				if(object instanceof FieldError) {
					fieldError = (FieldError) object;
				}
			}
			message = "Field: " + fieldError.getField() + " - Lỗi: " + fieldError.getCode();
			return new ModelAndView("redirect:/admin/product?index=0", "message", message);
		}
		//--------------------------------------------------------
		product.setUrlImage(uploadFileAdmin(file));
		productRepository.insert(product);
		return new ModelAndView("redirect:/admin/product?index=0", "message", message);

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
	//Sring mvc 
	@GetMapping(value = "/admin/product")
	public ModelAndView index(Model model, @RequestParam String index, HttpServletRequest req) {
		Page<Product> page = productRepository.findAll(PageRequest.of(Integer.parseInt(index), 12));
		List<Category> categories = categoryRepository.findAll();
		List<Supplier> suppliers = supplierRepository.findAll();
	
		model.addAttribute("listProduct", page.getContent());
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("currentPage", req.getParameter("index"));
		model.addAttribute("categories", categories);
		model.addAttribute("suppliers", suppliers);
		return new ModelAndView("ProductAdmin");
	}
	//
	@GetMapping(value = "/api/v1/product/detail")
	public ResponseEntity<CustomResponse> getProductById(@RequestParam String id) {
		if(productRepository.findById(id) == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Sản phẩm không tìm thấy", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Tìm thành công", productRepository.findById(id)), HttpStatus.OK);
	}

	@GetMapping(value = "/admin/product/delete")
	public ModelAndView deleteProductById(@RequestParam String id, Model model) {
		String message = "Xoá thành công";
		if(productRepository.findById(id).isPresent()!=false) {
			productRepository.deleteById(id);
		}
		Page<Product> page = productRepository.findAll(PageRequest.of(0, 12));
		model.addAttribute("listProduct", page.getContent());
		return new ModelAndView("redirect:/admin/product?index=0", "message", message);
	}
	@PostMapping(value = "/admin/product/update")
	public ModelAndView updateProductById(@ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file, BindingResult bindingResult) throws IOException {
		String message = "Cập nhật thành công";
		if(productRepository.findById(product.getId()).isPresent()!=false) {
			// check ---------------------------------
			productValidator.productValidation(product, bindingResult);
			if (bindingResult.hasErrors()) {
				FieldError fieldError = null;
				for (Object object : bindingResult.getAllErrors()) {
					if(object instanceof FieldError) {
						fieldError = (FieldError) object;
					}
				}
				message = "Field: " + fieldError.getField() + " - Lỗi: " + fieldError.getCode();
				return new ModelAndView("redirect:/admin/product?index=0", "message", message);

			} 
			// ----------------------------------------------
			Product productUpdated = productRepository.findById(product.getId()).get();
			productUpdated.setName(product.getName());
			productUpdated.setPrice(product.getPrice());
			productUpdated.setDescription(product.getDescription());
			productUpdated.setInventory(product.getInventory());
			productUpdated.setSupplier(product.getSupplier());
			if(file.getSize() != 0) {
				productUpdated.setUrlImage(uploadFileAdmin(file));
			}
			productUpdated.setCategory(product.getCategory());
			productRepository.save(productUpdated);
		} else {
			return new ModelAndView("redirect:/admin/product?index=0", "message", "Sản phẩm không tồn tại");
		}
		return new ModelAndView("redirect:/admin/product?index=0");
	}

	@GetMapping(value = "/api/v1/product/page") // phân trang
	public ResponseEntity<CustomResponse> getProductPageByIndex(@RequestParam int index) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getAuthorities());
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
	// tìm phía admin
	@GetMapping(value = "/admin/product/search")
	public ModelAndView adminSearch(@RequestParam String keyword, Model model) {
		List<Product> list = productRepository.findByTextSearch(keyword);
		
		model.addAttribute("listProduct",list);
		model.addAttribute("totalPage", 0);
		model.addAttribute("currentPage", 0);
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
