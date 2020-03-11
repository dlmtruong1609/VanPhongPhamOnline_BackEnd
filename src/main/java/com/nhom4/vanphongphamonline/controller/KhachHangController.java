package com.nhom4.vanphongphamonline.controller;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.jwt.JwtTokenProvider;
import com.nhom4.vanphongphamonline.model.KhachHang;
import com.nhom4.vanphongphamonline.model.SanPham;
import com.nhom4.vanphongphamonline.model.TaiKhoan;
import com.nhom4.vanphongphamonline.repository.KhachHangRepository;
import com.nhom4.vanphongphamonline.repository.RoleRepository;
import com.nhom4.vanphongphamonline.services.CustomTaiKhoanDetails;
import com.nhom4.vanphongphamonline.services.SecurityService;
import com.nhom4.vanphongphamonline.services.SecurityServiceImpl;
import com.nhom4.vanphongphamonline.services.ServiceStatus;
import com.nhom4.vanphongphamonline.validator.KhachHangValidator;
import com.nhom4.vanphongphamonline.validator.TaiKhoanValidator;
@Controller
public class KhachHangController {
	@Autowired
	private KhachHangRepository khachHangRepository;
	@Autowired
	private EmailController emailController;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TaiKhoanValidator taiKhoanValidator;
    @Autowired
    private KhachHangValidator khachHangValidator;
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
	@PostMapping(value = "/api/dangky", produces = MediaType.APPLICATION_JSON_VALUE) // application/json
	public ResponseEntity<ServiceStatus> createUser(@RequestBody TaiKhoan taiKhoan, BindingResult bindingResult) {
		// check -----------------------------
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
	   // ---------------------------------------
	   // mã hoá mật khẩu
		taiKhoan.setMatKhau(bCryptPasswordEncoder.encode(taiKhoan.getMatKhau()));
		taiKhoan.setMatKhauXacNhan(bCryptPasswordEncoder.encode(taiKhoan.getMatKhauXacNhan()));
		taiKhoan.setRoles(new HashSet<>(roleRepository.findByName("MEMBER")));
		KhachHang khachHang = new KhachHang();
		khachHang.setTaiKhoan(taiKhoan);
		khachHangRepository.insert(khachHang);
		// gửi email s khi đăng ký
//		emailController.sendEmail(taiKhoan.getEmail(), "ANANAS Đăng ký", "Chào mừng đến với kênh mua sắm trực tiếp của văn phòng phẩm ANANAS");
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Đăng ký thành công"), HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "/api/dangnhap", produces = MediaType.APPLICATION_JSON_VALUE) // application/json
	public ResponseEntity<ServiceStatus> login(@RequestBody TaiKhoan taiKhoan, BindingResult bindingResult) {
		// check ----------------------------------------
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
		//----------------------------------------------------
		// Xác nhận tài khoản mật khẩu
	 	   Authentication authentication = authenticationManager.authenticate(
	               new UsernamePasswordAuthenticationToken(
	                       taiKhoan.getTaiKhoan(),
	                       taiKhoan.getMatKhau()
	               )
	       );
	 	 // tự động generate token
        String jwt = tokenProvider.generateToken((CustomTaiKhoanDetails) authentication.getPrincipal());
        
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, jwt, taiKhoan), HttpStatus.OK);
	}
//	@PostMapping(value = "/api/dangxuat")
//	public ResponseEntity<ServiceStatus> logout(HttpServletRequest request, HttpServletResponse response) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (auth != null) {
//		    new SecurityContextLogoutHandler().logout(request, response, auth);
//		}
//		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Đăng xuất thành công"), HttpStatus.OK);
//	}
	
	@ResponseBody
	@PostMapping(value = "/api/khachhang/capnhat")
	public ResponseEntity<ServiceStatus> updateCustomerByUsername(@RequestBody KhachHang khachHang, @RequestParam String username, BindingResult bindingResult) {
		// check ---------------------------
		khachHangValidator.validate(khachHang, bindingResult);
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
		//---------------------------------------
		// lấy username từ context (biến chung của project) để so sánh
		if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username)) {
			try {
				KhachHang khachhangUpdated = khachHangRepository.findByUsername(username);
				khachhangUpdated.setTenKhachHang(khachHang.getTenKhachHang());
				khachhangUpdated.setDienThoai(khachHang.getDienThoai());
				khachhangUpdated.setCmnd(khachHang.getCmnd());
				khachHangRepository.save(khachhangUpdated);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(2, "Lỗi truy cập"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Cập nhật thông tin khách hàng thành công"), HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value = "/api/khachhang/chitiet")
	public ResponseEntity<ServiceStatus> getCustomerByUsername(@RequestParam String username) {
		KhachHang khachHang = null;
		// lấy username từ context (biến chung của project) để so sánh
		if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username)) {
			khachHang = khachHangRepository.findByUsername(username);
		} else {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không đúng tài khoản", null), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thành công", khachHang), HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value = "/api/quanly/khachhang/danhsach")
	public ResponseEntity<ServiceStatus> getAllCustomer() {
		List<KhachHang> list = null;
		list = khachHangRepository.findAll();
		if(list == null) {
			return new ResponseEntity<ServiceStatus>( new ServiceStatus(1, "Không có sản phẩm nào tồn tại"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>( new ServiceStatus(0, "Danh sách khách hàng", list), HttpStatus.OK);
	}
}
