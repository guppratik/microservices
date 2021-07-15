
package com.sample.service.product.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.DBRef;


public class ProductCategory {

	@Id
	private String id;
	private String name;
	private String title;
	private String description;
	private String imgUrl;
	public ProductCategory() {
		super();
	}
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

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Override
	public String toString() {
		return "ProductCategory [id=" + id + ", name=" + name + ", title=" + title + ", description=" + description
				+ ", imgUrl=" + imgUrl + "]";
	}



}
