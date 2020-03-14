package com.nhom4.vanphongphamonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.model.Supplier;
import com.nhom4.vanphongphamonline.repository.SupplierRepository;

@Controller
public class SupplierController {
	private SupplierRepository supplierRepository;
	@Autowired
	public SupplierController(SupplierRepository supplierRepository) {
		this.supplierRepository = supplierRepository;
		// TODO Auto-generated constructor stub
	}
	@ResponseBody
	@RequestMapping(value = "/nhacungcap/add", method = RequestMethod.POST)
	public String addNhaCungCap(@RequestParam("description")String description, @RequestParam("id") String id, @RequestParam("name") String name) {
		Supplier supplier = new Supplier(id, name, description);
		supplierRepository.insert(supplier);
		return "Đã thêm" + supplier;
	}
	@ResponseBody
	@RequestMapping("/nhacungcap/data")
	public List<Supplier> getAllNhaCungCap() {
		return supplierRepository.findAll();
	}
}
