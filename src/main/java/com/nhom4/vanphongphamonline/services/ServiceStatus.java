package com.nhom4.vanphongphamonline.services;

import java.util.List;

import com.nhom4.vanphongphamonline.model.HoaDon;
// cấu hình chung khi trả về response
public class ServiceStatus {
	private int code;
	private String message;
	private Object result;
	private List<Object> list;
	
	public List<Object> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
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
	public ServiceStatus(int code, String message, List<Object> list) {
		super();
		this.code = code;
		this.message = message;
		this.list = list;
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
