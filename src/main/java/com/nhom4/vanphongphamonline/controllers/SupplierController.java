package com.nhom4.vanphongphamonline.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
	@PostMapping(value = "/admin/supplier/add")
	public ModelAndView addSupplier( Supplier supplier) { // chưa bắt valid
		supplierRepository.insert(supplier);
		return new ModelAndView("redirect:/admin/supplier?index=0", "message", "Thêm thành công");
	}
	@GetMapping(value = "/admin/supplier/search")
	public ModelAndView adminSearch( @RequestParam String keyword, Model model) {
		List<Supplier> list = supplierRepository.findByText(keyword);
		model.addAttribute("listSupplier",list);
		model.addAttribute("totalPage", 0);
		model.addAttribute("currentPage", 0);
		return new ModelAndView("Supplier");
	}
	@GetMapping(value = "/admin/supplier")
	public ModelAndView index(Model model, @RequestParam String index, HttpServletRequest req) {
		Page<Supplier> page = supplierRepository.findAll(PageRequest.of(Integer.parseInt(index), 12));
		model.addAttribute("listSupplier", page.getContent());
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("currentPage", req.getParameter("index"));
		return new ModelAndView("Supplier");
	}
	@PostMapping(value = "/admin/supplier/delete")
	public ModelAndView deleteSupplierById(@RequestParam String id) {
		if(supplierRepository.findById(id).isPresent()!=false) {
			supplierRepository.deleteById(id);
		}
		return new ModelAndView("redirect:/admin/supplier?index=0", "message", "Xoá thành công");
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
	
	@PostMapping(value = "/admin/supplier/update")
	public ModelAndView updateSupplierById(@RequestParam String id,  Supplier supplier) { // chưa kiểm tra valid
		if(supplierRepository.findById(id).isPresent()!=false) {
			Supplier supplierUpdated = supplierRepository.findById(id).get();
			supplierUpdated.setName(supplier.getName());
			supplierUpdated.setDescription(supplier.getDescription());

			supplierRepository.save(supplierUpdated);
		} else {
			return new ModelAndView("redirect:/admin/supplier?index=0", "message", " không tồn tại");
		}
		return new ModelAndView("redirect:/admin/supplier?index=0", "message", "Cập nhật thành công");
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
