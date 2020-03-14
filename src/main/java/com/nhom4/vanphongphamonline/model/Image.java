package com.nhom4.vanphongphamonline.model;

import javax.persistence.Id;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Image {
	@Id
	private String id;
	private Binary img;
	private String title;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Binary getImg() {
		return img;
	}
	public void setImg(Binary img) {
		this.img = img;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Image(String title) {
		super();
		this.title = title;
	}
	
	
	
}
