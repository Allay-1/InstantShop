package com.bzbala.ad.bazarbala.product.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductClientRequest {

	private String productCode;

	private String productImage;

	private String orgin;

	private String govApproved;

	private String startDate;

	private String endDate;

	private String supplierId;

	private String language;

	private String name;

	private String description;

	private String quatityType;

	private Integer quantity;

	private String zipCode;

	private String brand;

	// Price Related Info
	private String currencyType;

	private Double basePrice;

	private Double sellPrice;

	// Discount Related Input
	private String discountType;

	private String discount;

	private String discountStartDate;

	private String discountEndDate;

	// Category Related Detail

	private String categoryCode;

	private String categoryName;

	private String categoryDescription;

	private String categoryImageId;

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

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public String getOrgin() {
		return orgin;
	}

	public void setOrgin(String orgin) {
		this.orgin = orgin;
	}

	public String getGovApproved() {
		return govApproved;
	}

	public void setGovApproved(String govApproved) {
		this.govApproved = govApproved;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public String getQuatityType() {
		return quatityType;
	}

	public void setQuatityType(String quatityType) {
		this.quatityType = quatityType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getCategoryImageId() {
		return categoryImageId;
	}

	public void setCategoryImageId(String categoryImageId) {
		this.categoryImageId = categoryImageId;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getDiscountStartDate() {
		return discountStartDate;
	}

	public void setDiscountStartDate(String discountStartDate) {
		this.discountStartDate = discountStartDate;
	}

	public String getDiscountEndDate() {
		return discountEndDate;
	}

	public void setDiscountEndDate(String discountEndDate) {
		this.discountEndDate = discountEndDate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	@Override
	public String toString() {
		return "ProductClientRequest [productCode=" + productCode + ", productImage=" + productImage + ", orgin="
				+ orgin + ", govApproved=" + govApproved + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", supplierId=" + supplierId + ", language=" + language + ", name=" + name + ", description="
				+ description + ", quatityType=" + quatityType + ", quantity=" + quantity + ", zipCode=" + zipCode
				+ ", brand=" + brand + ", currencyType=" + currencyType + ", basePrice=" + basePrice + ", sellPrice="
				+ sellPrice + ", discountType=" + discountType + ", discount=" + discount + ", discountStartDate="
				+ discountStartDate + ", discountEndDate=" + discountEndDate + ", categoryCode=" + categoryCode
				+ ", categoryName=" + categoryName + ", categoryDescription=" + categoryDescription
				+ ", categoryImageId=" + categoryImageId + "]";
	}
	
	

}
