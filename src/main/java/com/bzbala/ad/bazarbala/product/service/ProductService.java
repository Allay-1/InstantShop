package com.bzbala.ad.bazarbala.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bzbala.ad.bazarbala.product.model.Category;
import com.bzbala.ad.bazarbala.product.model.Discount;
import com.bzbala.ad.bazarbala.product.model.Price;
import com.bzbala.ad.bazarbala.product.model.Price.CurrencyType;
import com.bzbala.ad.bazarbala.product.model.ProductClientRequest;
import com.bzbala.ad.bazarbala.product.model.ProductDetail;
import com.bzbala.ad.bazarbala.product.model.ProductDetailResponse;
import com.bzbala.ad.bazarbala.product.model.Discount.Discount_Type;
import com.bzbala.ad.bazarbala.product.model.ProductDetail.Origin;
import com.bzbala.ad.bazarbala.product.model.ProductDetail.QuantityType;
import com.bzbala.ad.bazarbala.repository.Helper.ProductDetailRepository;

@Service
public class ProductService {

	@Autowired
	ProductDetailRepository productDetailRepository;
	
	@Autowired
	ProductDetailResponse productDetailResponse;

	public void createProductRequest(List<ProductClientRequest> productlistInRequest) {

		List<ProductDetail> productDetails=productlistInRequest.stream().map(item -> {

			Category category = new Category(item.getCategoryCode(), item.getSupplierId(), item.getCategoryName(),
					item.getCategoryDescription(), item.getCategoryImageId());

			Price price = new Price(item.getProductCode(), item.getSupplierId(), item.getBasePrice(),
					item.getSellPrice(), CurrencyType.valueOf(item.getCurrencyType()));

			Discount discount = new Discount(item.getProductCode(), item.getSupplierId(),
					Discount_Type.valueOf(item.getDiscountType()), item.getDiscount(), item.getDiscountStartDate(),
					item.getDiscountEndDate());

			ProductDetail productDetail = new ProductDetail(item.getProductCode(), item.getSupplierId(),
					item.getName(), item.getDescription(), QuantityType.valueOf(item.getQuatityType()),
					item.getQuantity(), item.getQuantity() != null && item.getQuantity() > 0 ? "True" : "False",
					Origin.valueOf(item.getOrgin()), item.getBrand(), item.getGovApproved(), item.getProductImage(),
					item.getStartDate(), item.getEndDate(), item.getCategoryCode(), price, discount, category);

			return productDetail;
		}).collect(Collectors.toList());
		
		save(productDetails);

	}
	
	
	@Transactional
	public void save(Iterable<ProductDetail> entities) {

		for (ProductDetail entity : entities) {
			productDetailRepository.save(entity);
		}

	}
	
	public ProductDetailResponse findAll() {
		List<ProductDetail> productList = new ArrayList<>();
		productDetailRepository.findAll().forEach(productList::add);

		productDetailResponse.setProductDetails(productList);
		return productDetailResponse;

	}
	
	public ProductDetailResponse findAllBySupplierId(String SupplierId) {
		List<ProductDetail> productList = new ArrayList<>();
		productDetailRepository.findBySupplierId(SupplierId).forEach(productList::add);
		productDetailResponse.setProductDetails(productList);
		return productDetailResponse;

	}
	
	public ProductDetailResponse findAllBySupplierIdAndCategoryId(String SupplierId,String CategoryCode) {
		List<ProductDetail> productList = new ArrayList<>();
		productDetailRepository.findSupplierIdAndCategoryCode(SupplierId,CategoryCode).forEach(productList::add);
		productDetailResponse.setProductDetails(productList);
		return productDetailResponse;

	}
	

}
