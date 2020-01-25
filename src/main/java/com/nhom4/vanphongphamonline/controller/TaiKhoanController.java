package com.nhom4.vanphongphamonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.model.TaiKhoan;
import com.nhom4.vanphongphamonline.repository.KhachHangRepository;
import com.nhom4.vanphongphamonline.repository.TaiKhoanRepository;
import com.nhom4.vanphongphamonline.validator.TaiKhoanValidator;
@Controller
public class TaiKhoanController {
	TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TaiKhoanValidator taiKhoanValidator;
	@Autowired
	public TaiKhoanController(TaiKhoanRepository taiKhoanRepository) {
		this.taiKhoanRepository = taiKhoanRepository;
		// TODO Auto-generated constructor stub
	}
	@ResponseBody
	@RequestMapping(value = "/dangky", method = RequestMethod.POST)
	public ResponseEntity<FieldError> createUser(@RequestBody TaiKhoan taiKhoan, BindingResult bindingResult) {
		taiKhoanValidator.validate(taiKhoan, bindingResult);
	   if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError(), HttpStatus.FAILED_DEPENDENCY);
        }
		taiKhoanRepository.save(taiKhoan);
		return new ResponseEntity<>(bindingResult.getFieldError(), HttpStatus.OK);
	}
}
