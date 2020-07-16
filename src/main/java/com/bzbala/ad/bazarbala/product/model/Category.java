package com.bzbala.ad.bazarbala.product.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "Category")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "productCode")
	private String productCode;

	@Column(name = "supplierId")
	private String supplierId;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "imageId")
	private String imageId;

//	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//	@JoinColumn(name = "categoryCode",referencedColumnName="productCode",insertable=false, updatable=false)
//	private List<ProductDetail> productDetails;

	public Category() {

	}
    

	public Category(String productCode, String supplierId, String name, String description, String imageId
			) {
		super();
		this.productCode = productCode;
		this.supplierId = supplierId;
		this.name = name;
		this.description = description;
		this.imageId = imageId;
		//this.productDetails = productDetails;
	}


//	public void setProductDetails(List<ProductDetail> productDetails) {
//		this.productDetails = productDetails;
//	}


	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}


	public String getImageId() {
		return imageId;
	}


	public void setImageId(String imageId) {
		this.imageId = imageId;
	}


//	public List<ProductDetail> getProductDetails() {
//		return productDetails;
//	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
