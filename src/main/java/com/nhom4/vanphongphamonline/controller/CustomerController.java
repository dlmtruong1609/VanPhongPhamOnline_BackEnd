package com.nhom4.vanphongphamonline.controller;

import java.util.Collections;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import com.nhom4.vanphongphamonline.model.Customer;
import com.nhom4.vanphongphamonline.model.Role;
import com.nhom4.vanphongphamonline.model.Product;
import com.nhom4.vanphongphamonline.model.Account;
import com.nhom4.vanphongphamonline.repository.CustomerRepository;
import com.nhom4.vanphongphamonline.repository.RoleRepository;
import com.nhom4.vanphongphamonline.services.CustomTaiKhoanDetails;
import com.nhom4.vanphongphamonline.services.SecurityService;
import com.nhom4.vanphongphamonline.services.SecurityServiceImpl;
import com.nhom4.vanphongphamonline.services.ServiceStatus;
import com.nhom4.vanphongphamonline.validator.CustomerValidator;
import com.nhom4.vanphongphamonline.validator.AccountValidator;
@Controller
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private EmailController emailController;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AccountValidator accountValidator;
    @Autowired
    private CustomerValidator customerValidator;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;
	@Autowired
	public CustomerController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
		// TODO Auto-generated constructor stub
	}
	@ResponseBody
	@PostMapping(value = "/api/v1/register", produces = MediaType.APPLICATION_JSON_VALUE) // application/json
	public ResponseEntity<ServiceStatus> createUser(@RequestBody Account account, BindingResult bindingResult) {
		// check -----------------------------
		accountValidator.validateFormRegister(account, bindingResult);
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
	    account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
	    account.setPasswordConfirm(bCryptPasswordEncoder.encode(account.getPasswordConfirm()));
	    account.setRoles(new HashSet<>(roleRepository.findByName("MEMBER")));
		Customer customer = new Customer();
		customer.setAccount(account);
		customerRepository.insert(customer);
		// gửi email s khi đăng ký
//		emailController.sendEmail(taiKhoan.getEmail(), "ANANAS Đăng ký", "Chào mừng đến với kênh mua sắm trực tiếp của văn phòng phẩm ANANAS");
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Đăng ký thành công"), HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "/api/v1/login", produces = MediaType.APPLICATION_JSON_VALUE) // application/json
	public ResponseEntity<ServiceStatus> login(@RequestBody Account account, BindingResult bindingResult) {
		// check ----------------------------------------
		accountValidator.validateFormLogin(account, bindingResult);
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
	            		   account.getUsername(),
	            		   account.getPassword()
	               )
	       );
	 	 // tự động generate token
        String jwt = tokenProvider.generateToken((CustomTaiKhoanDetails) authentication.getPrincipal());
        
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Đăng nhập thành công", jwt), HttpStatus.OK);
	}
	// kiểm tra có phải là role admin hay ko?
	private boolean hasRoleAdmin() {
		Customer customer = customerRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        for (Role role : customer.getAccount().getRoles()){
        	if(role.getName().equals("ADMIN")) {
        		return true;
        	}
        }
        return false;
	}
	@ResponseBody
	@PostMapping(value = "/api/v1/customer/update")
	public ResponseEntity<ServiceStatus> updateCustomerByUsername(@RequestBody Customer customer, @RequestParam String username, BindingResult bindingResult) {
		// check ---------------------------
		customerValidator.validate(customer, bindingResult);
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
		if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username) || hasRoleAdmin()) {
			try {
				Customer customerUpdated = customerRepository.findByUsername(username);
				customerUpdated.setName(customer.getName());
				customerUpdated.setPhone(customer.getPhone());
				customerUpdated.setIdentityCard(customer.getIdentityCard());
				customerUpdated.setAddress(customer.getAddress());
				customerUpdated.setBirthday(customer.getBirthday());
				customerRepository.save(customerUpdated);
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
	@GetMapping(value = "/api/v1/customer/detail")
	public ResponseEntity<ServiceStatus> getCustomerByUsername(@RequestParam String username) {
		Customer customer = null;
		// lấy username từ context (biến chung của project) để so sánh
		if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username) || hasRoleAdmin()) {
			customer = customerRepository.findByUsername(username);
		} else {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không đúng tài khoản", null), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thành công", customer), HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value = "/api/v1/admin/customer/list")
	public ResponseEntity<ServiceStatus> getAllCustomer() {
		List<Customer> list = null;
		list = customerRepository.findAll();
		if(list == null) {
			return new ResponseEntity<ServiceStatus>( new ServiceStatus(1, "Không có sản phẩm nào tồn tại"), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>( new ServiceStatus(0, "Danh sách khách hàng", list), HttpStatus.OK);
	}
}
