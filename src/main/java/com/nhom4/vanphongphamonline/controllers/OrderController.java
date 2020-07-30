package com.nhom4.vanphongphamonline.controllers;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nhom4.vanphongphamonline.models.Category;
import com.nhom4.vanphongphamonline.models.Customer;
import com.nhom4.vanphongphamonline.models.EmailContent;
import com.nhom4.vanphongphamonline.models.Order;
import com.nhom4.vanphongphamonline.models.Product;
import com.nhom4.vanphongphamonline.models.Role;
import com.nhom4.vanphongphamonline.models.Supplier;
import com.nhom4.vanphongphamonline.repositories.CustomerRepository;
import com.nhom4.vanphongphamonline.repositories.OrderDetailRepository;
import com.nhom4.vanphongphamonline.repositories.OrderRepository;
import com.nhom4.vanphongphamonline.repositories.ProductRepository;
import com.nhom4.vanphongphamonline.services.CustomerService;
import com.nhom4.vanphongphamonline.utils.CustomResponse;
import com.nhom4.vanphongphamonline.validators.OrderValidator;

@RestController
public class OrderController {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderValidator orderValidator;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private EmailController emailController;
	@Autowired
	public OrderController(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	private CustomerService customerService;
	
	@GetMapping(value = "/admin/order")
	public ModelAndView index(Model model, @RequestParam String index, HttpServletRequest req) {
		Page<Order> page = orderRepository.findAll(PageRequest.of(Integer.parseInt(index), 12));
	
		model.addAttribute("listOrder", page.getContent());
		model.addAttribute("totalPage", page.getTotalPages());
		model.addAttribute("currentPage", req.getParameter("index"));
		return new ModelAndView("Order");
	}
	@GetMapping(value = "/admin/order/search")
	public ModelAndView adminSearch(@RequestParam String keyword, Model model) {
		List<Order> list = orderRepository.findByTextSearch(keyword);
		System.out.println(list);
		model.addAttribute("listOrder", list);
		model.addAttribute("totalPage", 0);
		model.addAttribute("currentPage", 0);
		return new ModelAndView("Order");
	}
	@PostMapping(value = "/api/v1/order/pay") // sử dụng khi thanh toán ko dùng để add vào giỏ hàng
	public ResponseEntity<CustomResponse> createOrder(@RequestBody Order order, BindingResult bindingResult, HttpServletRequest request) throws ParseException {
		// check ---------------------------------------
		orderValidator.validate(order, bindingResult);
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
	    		return new ResponseEntity<CustomResponse>(new CustomResponse(4, "Sản phẩm " + product.get().getName() + " chỉ còn " + product.get().getInventory() + " sản phẩm", null), HttpStatus.OK);
	    	} else {
	    		product.get().setInventory(inventory - quantity);
	    		productRepository.save(product.get());
	    	}
		} // ------------------------------
		order.setBillDate(new Date());
		orderRepository.insert(order);
		System.out.println(order.getCustomer().getAccount().getEmail());
		emailController.sendEmail(new EmailContent(order.getCustomer().getAccount().getEmail(), "ANANAS Đơn đặt mua thành công", "Chúc mừng quý khách đã đặt hàng thành công sản phẩm của chúng tôi, kính chúc quý khách một ngày tốt lành"));
		HttpSession session = request.getSession();
		session.invalidate();
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Thanh toán thành công", null), HttpStatus.OK);
	}
	@GetMapping(value = "/api/v1/order/detail")
	public ResponseEntity<CustomResponse> getOrderById(@RequestParam String id, @RequestParam String username) {
		Optional<Order> order = null;
		if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username) || customerService.hasRoleAdmin()) {
			order = orderRepository.findById(id);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Chi tiết hoá đơn", order), HttpStatus.OK);
	}
	@GetMapping(value = "/api/v1/order/list")
	public ResponseEntity<CustomResponse> getAllOrder(@RequestParam String username) {
		List<Order> list = null;
		if(SecurityContextHolder.getContext().getAuthentication().getName().equals(username) || customerService.hasRoleAdmin()) {
			list = orderRepository.getAllOrderByCustomer_Account_Username(username);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Danh sách hoá đơn", list), HttpStatus.OK);
	}
}
