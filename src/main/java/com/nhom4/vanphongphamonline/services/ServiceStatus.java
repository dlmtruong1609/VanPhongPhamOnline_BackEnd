package com.nhom4.vanphongphamonline.services;

public class ServiceStatus {
	private int code;
	private String message;
	private String resutl;
	
	public String getResutl() {
		return resutl;
	}
	public void setResutl(String resutl) {
		this.resutl = resutl;
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
	
	public ServiceStatus(int code, String message, String resutl) {
		super();
		this.code = code;
		this.message = message;
		this.resutl = resutl;
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
