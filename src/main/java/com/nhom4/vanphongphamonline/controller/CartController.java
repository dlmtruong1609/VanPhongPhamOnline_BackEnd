package com.nhom4.vanphongphamonline.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
import org.springframework.web.server.WebSession;

import com.nhom4.vanphongphamonline.model.OrderDetail;
import com.nhom4.vanphongphamonline.model.Order;
import com.nhom4.vanphongphamonline.model.Product;
import com.nhom4.vanphongphamonline.repository.CustomerRepository;
import com.nhom4.vanphongphamonline.services.ServiceStatus;
import com.nhom4.vanphongphamonline.validator.OrderValidator;

@Controller
public class CartController {
//	Tự động tạo ID bắt dầu từ 0
//	@Autowired
//	private static AtomicInteger ID_GENERATOR = new AtomicInteger(0);
	List<OrderDetail> list; //list để add chi tiet hoa đơn khi thêm
	@Autowired
	OrderValidator orderValidator; // valid hoá đơn
	@Autowired
	CustomerRepository customerRepository;
	double total = 0; // tính tổng tiền của hoá đơn
	@ResponseBody
	@PostMapping(value = "/api/v1/cart/add")
	public ResponseEntity<ServiceStatus> saveOrder(HttpServletRequest request, @RequestBody OrderDetail orderDetail, @RequestParam String username, BindingResult bindingResult) {
		HttpSession session = request.getSession(); // lấy current session
		Order orderSession = (Order) session.getAttribute("order"); // lấy thuộc tính mang tên order
		// check ------------------------------
		orderValidator.validateOrderDetail(orderDetail, bindingResult);
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
//		-------------------------------------
		list = new ArrayList<OrderDetail>();
		list.add(orderDetail);
		if(orderSession!= null) { 
			for (int i = 0; i < orderSession.getListOrderDetail().size(); i++) {
				OrderDetail cthd = orderSession.getListOrderDetail().get(i);
				if(cthd.getProduct().getId().equals(orderDetail.getProduct().getId())) {
					if((cthd.getQuantity() + orderDetail.getQuantity()) > cthd.getProduct().getInventory()) {
						return new ResponseEntity<ServiceStatus>(new ServiceStatus(5, "Số lượng đặt vượt quá số lượng trong kho, vui lòng xem lại giỏ hàng"), HttpStatus.OK);
					}
					cthd.setQuantity(cthd.getQuantity() + orderDetail.getQuantity()); // tính số lượng
					cthd.setUnitPrice(cthd.getUnitPrice() + orderDetail.getUnitPrice()); // tính đơn giá
					// remove chi tiet hoá đơn là vì khi nếu cùng sản phẩm được thêm vào giỏ hàng thì chỉ cần
					// tăng số lượng lên thôi, ko cần phải add thêm 1 sản phẩm nữa sẽ gây trùng lặp lại
					list.remove(orderDetail); 
				} 
				if(!orderSession.getListOrderDetail().contains(orderDetail)){
					// xử lý khi thêm 1 sản phẩm mới ko có trong giỏ hàng
					if(orderDetail.getProduct().getId() != cthd.getProduct().getId()) {
						total += orderDetail.getUnitPrice();
					}
				}
				total += cthd.getUnitPrice(); 
			}
			orderSession.setTotalMoney(total);
			total = 0;
			orderSession.getListOrderDetail().addAll(list);
			orderSession.setListOrderDetail(orderSession.getListOrderDetail());
		} else { // orderSession là null
			orderSession = new Order();
			orderSession.setTotalMoney(orderDetail.getUnitPrice());
			orderSession.setListOrderDetail(list);
		}
		orderSession.setCustomer(customerRepository.findByUsername(username));
		session.setAttribute("order", orderSession);
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thêm thành con vào giỏ hàng"), HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "/api/v1/cart/update") // id cua sanpham, action la tang hoac giam
	public ResponseEntity<ServiceStatus> updateOrder(@RequestBody Order order, HttpServletRequest request, BindingResult bindingResult) {
		HttpSession session = request.getSession();
		for (int i = 0; i < order.getListOrderDetail().size(); i++) {
			OrderDetail orderDetail = order.getListOrderDetail().get(i);
			// check ----------------------
			orderValidator.validateOrderDetail(orderDetail, bindingResult);
			total += orderDetail.getUnitPrice(); 
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
			//-------------------------------
		}
		order.setTotalMoney(total);
		total = 0;
		session.setAttribute("order", order);
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Cập nhật thành công", session.getAttribute("order")), HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/api/v1/cart/data")
	public ResponseEntity<ServiceStatus> getOrderInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("order") == null) {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có sản phẩm trong giỏ hàng", ""), HttpStatus.OK);
		}
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thành công", session.getAttribute("order")), HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "/api/v1/cart/delete") // id la ma san pham
	public ResponseEntity<ServiceStatus> removeOrder(@RequestParam String id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Order order = (Order) session.getAttribute("order");
		if(order.getListOrderDetail() != null) {
			for (int i = 0; i < order.getListOrderDetail().size(); i++) {
				if(order.getListOrderDetail().get(i).getProduct().getId().equals(id)) {
					order.setTotalMoney(order.getTotalMoney() - order.getListOrderDetail().get(i).getUnitPrice());;
					order.getListOrderDetail().remove(i);
				}
			}
		} else {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Không có sản phẩm trong giỏ hàng", ""), HttpStatus.OK);
		}
		session.setAttribute("order", order);
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Thành công", session.getAttribute("order")), HttpStatus.OK);
	}
}
