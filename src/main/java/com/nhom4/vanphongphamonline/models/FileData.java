package com.nhom4.vanphongphamonline.models;

import javax.persistence.GeneratedValue;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

public class FileData {
	@GeneratedValue
	@Id
	private String id;
	
	private String name;
	
	private String type;
	
	private Binary file;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Binary getFile() {
		return file;
	}

	public void setData(Binary file) {
		this.file = file;
	}

	public FileData(String name, String type, Binary file) {
		super();
		this.name = name;
		this.type = type;
		this.file = file;
	}

	public FileData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
