package com.nhom4.vanphongphamonline.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Address {
	private String city;
	
	private String district;
	
	private String ward;
	
	private String town;
	
	private String street;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Address(String city, String district, String ward, String town, String street) {
		super();
		this.city = city;
		this.district = district;
		this.ward = ward;
		this.town = town;
		this.street = street;
	}
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Address [city=" + city + ", district=" + district + ", ward=" + ward + ", town=" + town + ", street="
				+ street + "]";
	}
	
}
