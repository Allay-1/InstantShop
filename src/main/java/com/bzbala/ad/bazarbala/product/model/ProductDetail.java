package com.bzbala.ad.bazarbala.product.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "ProductDetail")
public class ProductDetail 
implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public enum QuantityType {
	    KG,
	    NUMBER,
	    LITER
	  }
	
	public enum Origin {
		INDIA,
		MADE_IN_CHAINA,
		OTHERS
	}
	
	@Id
	@Column(name="productCode", nullable=false)
	private String productCode;
	
	@Column(name="supplierId", nullable=false)
	private String supplierId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name="quantityType")
	private QuantityType quantityType;
	
	@Column(name="quantity")
	private Integer quantity;
	
	@Column(name="stock")
	private String stock;
	
	@Enumerated(EnumType.STRING)
	@Column(name="origin")
	private Origin origin;
	
	@Column(name="brand")
    private String brand;
	
	@Column(name="govApproved")
	private String govApproved;
	
	@Column(name="image")
	private String image;
	
	@Column(name="startDate")
	private String startDate;
	
	@Column(name="endDate")
	private String endDate;
	
	@Column(name="categoryCode")
	private String categoryCode;
	
	@OneToOne(fetch = FetchType.EAGER,optional=false,cascade = CascadeType.ALL)
	@JoinColumn(name = "productCode",referencedColumnName="productCode",insertable=false, updatable=false)
	@JoinColumn(name = "supplierId",referencedColumnName="supplierId",insertable=false, updatable=false)
	private Price price;
	
	@OneToOne(fetch = FetchType.EAGER,optional=false,cascade = CascadeType.ALL)
	@JoinColumn(name = "productCode",referencedColumnName="productCode",insertable=false, updatable=false)
	@JoinColumn(name = "supplierId",referencedColumnName="supplierId",insertable=false, updatable=false)
	private Discount discount;
	
	@ManyToOne(fetch = FetchType.EAGER,optional=false,cascade = CascadeType.ALL)
	@JoinColumn(name = "categoryCode",referencedColumnName="productCode",insertable=false, updatable=false)
	@JoinColumn(name = "supplierId",referencedColumnName="supplierId",insertable=false, updatable=false)
	private Category category;
	
    public ProductDetail() {
		
	}
	
	public ProductDetail(String productCode, String supplierId, String name, String description,
			QuantityType quantityType,Integer quantity, String stock, Origin origin, String brand, String govApproved, String image,
			String startDate, String endDate, String categoryCode, Price price, Discount discount, Category category) {
		super();
		this.productCode = productCode;
		this.supplierId = supplierId;
		this.name = name;
		this.description = description;
		this.quantityType = quantityType;
		this.quantity = quantity;
		this.stock = stock;
		this.origin = origin;
		this.brand = brand;
		this.govApproved = govApproved;
		this.image = image;
		this.startDate = startDate;
		this.endDate = endDate;
		this.categoryCode = categoryCode;
		this.price = price;
		this.discount = discount;
		this.category = category;
	}

	
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


	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getGovApproved() {
		return govApproved;
	}

	public void setGovApproved(String govApproved) {
		this.govApproved = govApproved;
	}

	

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public QuantityType getQuantityType() {
		return quantityType;
	}

	public void setQuantityType(QuantityType quantityType) {
		this.quantityType = quantityType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
	
	
}
