package com.nhom4.vanphongphamonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.model.TaiKhoan;
import com.nhom4.vanphongphamonline.repository.TaiKhoanRepository;
import com.nhom4.vanphongphamonline.services.SecurityService;
import com.nhom4.vanphongphamonline.services.SecurityServiceImpl;
import com.nhom4.vanphongphamonline.services.ServiceStatus;
import com.nhom4.vanphongphamonline.services.TaiKhoanServiceImpl;
import com.nhom4.vanphongphamonline.validator.TaiKhoanValidator;
@Controller
public class TaiKhoanController {
	@Autowired
	TaiKhoanServiceImpl taiKhoanServiceImpl;
	@Autowired
	EmailController emailController;
    @Autowired
    private TaiKhoanValidator taiKhoanValidator;
	@Autowired
	public TaiKhoanController(TaiKhoanServiceImpl taiKhoanServiceImpl) {
		this.taiKhoanServiceImpl = taiKhoanServiceImpl;
		// TODO Auto-generated constructor stub
	}
	@ResponseBody
	@PostMapping(value = "/dangky", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ServiceStatus> createUser(@RequestBody TaiKhoan taiKhoan, BindingResult bindingResult) {
		taiKhoanValidator.validateFormRegister(taiKhoan, bindingResult);
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
		taiKhoanServiceImpl.save(taiKhoan);
		emailController.sendEmail(taiKhoan.getEmail(), "ANANAS Đăng ký", "Chào mừng đến với kênh mua sắm trực tiếp của văn phòng phẩm ANANAS");
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Đăng ký thành công"), HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "/dangnhap", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ServiceStatus> login(@RequestBody TaiKhoan taiKhoan, BindingResult bindingResult) {
		taiKhoanValidator.validateFormLogin(taiKhoan, bindingResult);
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
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Đăng nhập thành công"), HttpStatus.OK);
	}
}
