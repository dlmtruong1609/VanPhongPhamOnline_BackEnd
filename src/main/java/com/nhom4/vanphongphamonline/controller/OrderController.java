package com.nhom4.vanphongphamonline.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.model.Order;
import com.nhom4.vanphongphamonline.model.Product;
import com.nhom4.vanphongphamonline.model.Customer;
import com.nhom4.vanphongphamonline.repository.OrderDetailRepository;
import com.nhom4.vanphongphamonline.repository.OrderRepository;
import com.nhom4.vanphongphamonline.repository.ProductRepository;
import com.nhom4.vanphongphamonline.services.ServiceStatus;
import com.nhom4.vanphongphamonline.validator.OrderValidator;

@Controller
public class OrderController {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderValidator orderValidator;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	public OrderController(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	@ResponseBody
	@PostMapping(value = "/api/v1/order/pay") // sử dụng khi thanh toán ko dùng để add vào giỏ hàng
	public ResponseEntity<ServiceStatus> createOrder(@RequestBody Order order, BindingResult bindingResult, HttpServletRequest request) throws ParseException {
		// check ---------------------------------------
		orderValidator.validate(order, bindingResult);
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
		//--------------------------------------------------
		// kiểm tra inventory
		for (int i = 0; i < order.getListOrderDetail().size(); i++) {
	    	Optional<Product> product = productRepository.findById(order.getListOrderDetail().get(i).getProduct().getId());
	    	int inventory = product.get().getInventory();
	    	int quantity = order.getListOrderDetail().get(i).getQuantity(); // số lượng đặt mua
	    	System.out.println(inventory < quantity);
	    	System.out.println(inventory);
	    	System.out.println(quantity);
	    	if(inventory < quantity) {
	    		return new ResponseEntity<ServiceStatus>(new ServiceStatus(4, "Sản phẩm " + product.get().getName() + " chỉ còn " + product.get().getInventory() + " sản phẩm"), HttpStatus.OK);
	    	} else {
	    		product.get().setInventory(inventory - quantity);
	    		productRepository.save(product.get());
	    	}
		} // ------------------------------
		order.setBillDate(new Date());
		orderRepository.insert(order);
		HttpSession session = request.getSession();
		session.invalidate();
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thanh toán thành công"), HttpStatus.OK);
	}
	@ResponseBody
	@GetMapping(value = "/api/v1/order/detail")
	public ResponseEntity<ServiceStatus> getOrderById(@RequestParam String id, @RequestParam String username) {
		Optional<Order> order = null;
		if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username)) {
			order = orderRepository.findById(id);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Chi tiết hoá đơn", order), HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/api/v1/order/list")
	public ResponseEntity<ServiceStatus> getAllOrder(@RequestParam String username) {
		List<Order> list = null;
		if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username)) {
			list = orderRepository.getAllOrderByCustomer_Account_Username(username);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Danh sách hoá đơn", list), HttpStatus.OK);
	}
}
