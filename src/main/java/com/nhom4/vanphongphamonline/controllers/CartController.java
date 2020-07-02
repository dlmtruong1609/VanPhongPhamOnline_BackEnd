package com.nhom4.vanphongphamonline.controllers;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nhom4.vanphongphamonline.models.Order;
import com.nhom4.vanphongphamonline.models.OrderDetail;
import com.nhom4.vanphongphamonline.repositories.CustomerRepository;
import com.nhom4.vanphongphamonline.utils.CustomResponse;
import com.nhom4.vanphongphamonline.validators.OrderValidator;

@RestController
public class CartController {
	private List<OrderDetail> list; //list để add chi tiet hoa đơn khi thêm
	@Autowired
	private OrderValidator orderValidator; // valid hoá đơn
	@Autowired
	private CustomerRepository customerRepository;
	private double total = 0; // tính tổng tiền của hoá đơn
	@PostMapping(value = "/api/v1/cart/add")
	public ResponseEntity<CustomResponse> saveOrder(HttpServletRequest request, @RequestBody OrderDetail orderDetail, @RequestParam String username, BindingResult bindingResult) {
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
			   CustomResponse serviceStatusError = new CustomResponse(Integer.parseInt(fieldError.getDefaultMessage()), String.valueOf(fieldError.getCode()), null);
		   
		   return new ResponseEntity<CustomResponse>(serviceStatusError, HttpStatus.OK);
        }
//		-------------------------------------
		list = new ArrayList<OrderDetail>();
		list.add(orderDetail);
		if(orderSession!= null) { 
			for (int i = 0; i < orderSession.getListOrderDetail().size(); i++) {
				OrderDetail cthd = orderSession.getListOrderDetail().get(i);
				if(cthd.getProduct().getId().equals(orderDetail.getProduct().getId())) {
					if((cthd.getQuantity() + orderDetail.getQuantity()) > cthd.getProduct().getInventory()) {
						return new ResponseEntity<CustomResponse>(new CustomResponse(5, "Số lượng đặt vượt quá số lượng trong kho, vui lòng xem lại giỏ hàng", null), HttpStatus.OK);
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
		orderSession.setCustomer(customerRepository.findByAccount_Username(username));
		session.setAttribute("order", orderSession);
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Thêm thành con vào giỏ hàng", null), HttpStatus.OK);
	}

	@PostMapping(value = "/api/v1/cart/update") // id cua sanpham, action la tang hoac giam
	public ResponseEntity<CustomResponse> updateOrder(@RequestBody Order order, HttpServletRequest request, BindingResult bindingResult) {
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
				   CustomResponse serviceStatusError = new CustomResponse(Integer.parseInt(fieldError.getDefaultMessage()), String.valueOf(fieldError.getCode()), null);
			   
			   return new ResponseEntity<CustomResponse>(serviceStatusError, HttpStatus.OK);
	        }
			//-------------------------------
		}
		order.setTotalMoney(total);
		total = 0;
		session.setAttribute("order", order);
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Cập nhật thành công", session.getAttribute("order")), HttpStatus.OK);
	}

	@GetMapping(value = "/api/v1/cart/data")
	public ResponseEntity<CustomResponse> getOrderInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("order") == null) {
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Không có sản phẩm trong giỏ hàng", ""), HttpStatus.OK);
		}
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Thành công", session.getAttribute("order")), HttpStatus.OK);
	}
	@PostMapping(value = "/api/v1/cart/delete") // id la ma san pham
	public ResponseEntity<CustomResponse> removeOrder(@RequestParam String id, HttpServletRequest request) {
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
			return new ResponseEntity<CustomResponse>(new CustomResponse(1, "Không có sản phẩm trong giỏ hàng", ""), HttpStatus.OK);
		}
		session.setAttribute("order", order);
		return new ResponseEntity<CustomResponse>(new CustomResponse(0, "Thành công", session.getAttribute("order")), HttpStatus.OK);
	}
}
