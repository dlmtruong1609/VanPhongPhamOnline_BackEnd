package com.nhom4.vanphongphamonline.services;

public class ServiceStatus {
	private int code;
	private String message;
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
	public ServiceStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ServiceStatus [code=" + code + ", message=" + message + "]";
	}
	
	
}
