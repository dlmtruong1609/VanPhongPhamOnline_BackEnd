package com.nhom4.vanphongphamonline.model;

import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class NhaCungCap {
	@GeneratedValue
	@Id
	private String maNhaCC;
	@Field
	private String tenNhaCC;
	@Field
	private String moTa;
	public String getMaNhaCC() {
		return maNhaCC;
	}
	public void setMaNhaCC(String maNhaCC) {
		this.maNhaCC = maNhaCC;
	}
	public String getTenNhaCC() {
		return tenNhaCC;
	}
	public void setTenNhaCC(String tenNhaCC) {
		this.tenNhaCC = tenNhaCC;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public NhaCungCap(String maNhaCC, String tenNhaCC, String moTa) {
		super();
		this.maNhaCC = maNhaCC;
		this.tenNhaCC = tenNhaCC;
		this.moTa = moTa;
	}
	public NhaCungCap() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "NhaCungCap [maNhaCC=" + maNhaCC + ", tenNhaCC=" + tenNhaCC + ", moTa=" + moTa + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maNhaCC == null) ? 0 : maNhaCC.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhaCungCap other = (NhaCungCap) obj;
		if (maNhaCC == null) {
			if (other.maNhaCC != null)
				return false;
		} else if (!maNhaCC.equals(other.maNhaCC))
			return false;
		return true;
	}
	
	
}
