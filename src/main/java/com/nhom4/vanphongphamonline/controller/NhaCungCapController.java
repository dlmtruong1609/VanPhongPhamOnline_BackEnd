package com.nhom4.vanphongphamonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.model.NhaCungCap;
import com.nhom4.vanphongphamonline.repository.NhaCungCapRepository;

@Controller
public class NhaCungCapController {
	private NhaCungCapRepository nhaCungCapRepository;
	@Autowired
	public NhaCungCapController(NhaCungCapRepository nhaCungCapRepository) {
		this.nhaCungCapRepository= nhaCungCapRepository;
		// TODO Auto-generated constructor stub
	}
	@ResponseBody
	@RequestMapping(value = "/nhacungcap/add", method = RequestMethod.POST)
	public String addNhaCungCap(@RequestParam("moTa")String moTa, @RequestParam("id") String id, @RequestParam("tenNhaCungCap") String tenNhaCC) {
		NhaCungCap nhaCungCap = new NhaCungCap(id, tenNhaCC, moTa);
		nhaCungCapRepository.insert(nhaCungCap);
		return "Đã thêm" + nhaCungCap;
	}
	@ResponseBody
	@RequestMapping("/nhacungcap/data")
	public List<NhaCungCap> getAllNhaCungCap() {
		return nhaCungCapRepository.findAll();
	}
}
