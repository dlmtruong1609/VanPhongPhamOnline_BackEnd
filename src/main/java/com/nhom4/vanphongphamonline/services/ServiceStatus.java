package com.nhom4.vanphongphamonline.services;

import java.util.List;

import com.nhom4.vanphongphamonline.model.Order;
// cấu hình chung khi trả về response
public class ServiceStatus {
	private int code;
	private String message;
	private Object result;
	

	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ServiceStatus(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public ServiceStatus(int code, String message, Object result) {
		super();
		this.code = code;
		this.message = message;
		this.result = result;
	}
	public ServiceStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ServiceStatus [code=" + code + ", message=" + message + "]";
	}
	
	
}
