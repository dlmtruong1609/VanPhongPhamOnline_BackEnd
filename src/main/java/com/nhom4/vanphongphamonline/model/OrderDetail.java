package com.nhom4.vanphongphamonline.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


public class OrderDetail {
	@Field
	private Product product;
	@Field
	private double unitPrice;
	@Field
	private int quantity;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public OrderDetail(Product product, double unitPrice, int quantity) {
		super();
		this.product = product;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}
	public OrderDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "OrderDetail [product=" + product + ", unitPrice=" + unitPrice + ", quantity=" + quantity + "]";
	}
	
	
}
