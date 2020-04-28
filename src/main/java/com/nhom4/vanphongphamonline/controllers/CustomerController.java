package com.nhom4.vanphongphamonline.controllers;

import java.util.HashSet;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.jwt.JwtTokenProvider;
import com.nhom4.vanphongphamonline.models.Account;
import com.nhom4.vanphongphamonline.models.Customer;
import com.nhom4.vanphongphamonline.repositories.CustomerRepository;
import com.nhom4.vanphongphamonline.repositories.RoleRepository;
import com.nhom4.vanphongphamonline.services.CustomAccountDetails;
import com.nhom4.vanphongphamonline.services.CustomerService;
import com.nhom4.vanphongphamonline.utils.CustomResponse;
import com.nhom4.vanphongphamonline.validators.AccountValidator;
import com.nhom4.vanphongphamonline.validators.CustomerValidator;
@Controller
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerService customerService;
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
	public ResponseEntity<CustomResponse> createUser(@RequestBody Account account, BindingResult bindingResult) {
		// check -----------------------------
		accountValidator.validateFormRegister(account, bindingResult);
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
	   // ---------------------------------------
	   // mã hoá mật khẩu
	    account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
	    account.setPasswordConfirm(bCryptPasswordEncoder.encode(account.getPasswordConfirm()));
	    account.setRoles(new HashSet<>(roleRepository.findByName("MEMBER")));
		Customer customer = new Customer();
		customer.setAccount(account);
		customerRepository.insert(customer);
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Đăng ký thành công", null), HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "/api/v1/login", produces = MediaType.APPLICATION_JSON_VALUE) // application/json
	public ResponseEntity<CustomResponse> login(@RequestBody Account account, BindingResult bindingResult) {
		// check ----------------------------------------
		accountValidator.validateFormLogin(account, bindingResult);
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
		//----------------------------------------------------
		// Xác nhận tài khoản mật khẩu
	 	   Authentication authentication = authenticationManager.authenticate(
	               new UsernamePasswordAuthenticationToken(
	            		   account.getUsername(),
	            		   account.getPassword()
	               )
	       );
	 	 // tự động generate token
        String jwt = tokenProvider.generateToken((CustomAccountDetails) authentication.getPrincipal());
        
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Đăng nhập thành công", jwt), HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "/api/v1/customer/update")
	public ResponseEntity<CustomResponse> updateCustomerByUsername(@RequestBody Customer customer, @RequestParam String username, BindingResult bindingResult) {
		// check ---------------------------
		customerValidator.validate(customer, bindingResult);
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
		//---------------------------------------
		// lấy username từ context (biến chung của project) để so sánh
		if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username) || customerService.hasRoleAdmin()) {
			try {
				Customer customerUpdated = customerRepository.findByAccount_Username(username);
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
			return new ResponseEntity<CustomResponse>(new CustomResponse(2, "Lỗi truy cập", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Cập nhật thông tin khách hàng thành công", null), HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value = "/api/v1/customer/detail")
	public ResponseEntity<CustomResponse> getCustomerByUsername(@RequestParam String username) {
		Customer customer = null;
		// lấy username từ context (biến chung của project) để so sánh
		if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username) || customerService.hasRoleAdmin()) {
			customer = customerRepository.findByAccount_Username(username);
		} else {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Không đúng tài khoản", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Thành công", customer), HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value = "/api/v1/admin/customer/list")
	public ResponseEntity<CustomResponse> getAllCustomer() {
		List<Customer> list = null;
		list = customerRepository.findAll();
		if(list == null) {
			return new ResponseEntity<CustomResponse>( new CustomResponse(1, "Không có sản phẩm nào tồn tại", null), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>( new CustomResponse(0, "Danh sách khách hàng", list), HttpStatus.OK);
	}
}