package com.nhom4.vanphongphamonline.controller;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
//import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.jwt.JwtTokenProvider;
import com.nhom4.vanphongphamonline.model.KhachHang;
import com.nhom4.vanphongphamonline.model.TaiKhoan;
import com.nhom4.vanphongphamonline.repository.KhachHangRepository;
import com.nhom4.vanphongphamonline.repository.RoleRepository;
import com.nhom4.vanphongphamonline.services.CustomTaiKhoanDetails;
import com.nhom4.vanphongphamonline.services.SecurityService;
import com.nhom4.vanphongphamonline.services.SecurityServiceImpl;
import com.nhom4.vanphongphamonline.services.ServiceStatus;
import com.nhom4.vanphongphamonline.validator.TaiKhoanValidator;
@Controller
public class KhachHangController {
	@Autowired
	KhachHangRepository khachHangRepository;
	@Autowired
	EmailController emailController;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TaiKhoanValidator taiKhoanValidator;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;
	@Autowired
	public KhachHangController(KhachHangRepository khachHangRepository) {
		this.khachHangRepository = khachHangRepository;
		// TODO Auto-generated constructor stub
	}
	@ResponseBody
	@PostMapping(value = "/api/dangky", produces = MediaType.APPLICATION_JSON_VALUE)
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
		taiKhoan.setMatKhau(bCryptPasswordEncoder.encode(taiKhoan.getMatKhau()));
		taiKhoan.setMatKhauXacNhan(bCryptPasswordEncoder.encode(taiKhoan.getMatKhauXacNhan()));
		taiKhoan.setRoles(new HashSet<>(roleRepository.findAll()));
		KhachHang khachHang = new KhachHang();
		khachHang.setTaiKhoan(taiKhoan);
		khachHangRepository.insert(khachHang);
		emailController.sendEmail(taiKhoan.getEmail(), "ANANAS Đăng ký", "Chào mừng đến với kênh mua sắm trực tiếp của văn phòng phẩm ANANAS");
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Đăng ký thành công"), HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "/api/dangnhap", produces = MediaType.APPLICATION_JSON_VALUE)
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
		// Trả về jwt cho người dùng.
	 	   Authentication authentication = authenticationManager.authenticate(
	               new UsernamePasswordAuthenticationToken(
	                       taiKhoan.getTaiKhoan(),
	                       taiKhoan.getMatKhau()
	               )
	       );
        String jwt = tokenProvider.generateToken((CustomTaiKhoanDetails) authentication.getPrincipal());
        
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, jwt), HttpStatus.OK);
	}
}
