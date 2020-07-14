package com.bzbala.ad.bazarbala.repository.Helper;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bzbala.ad.bazarbala.product.model.ProductDetail;
@Repository
public interface ProductDetailRepository extends CrudRepository<ProductDetail, String>{
	
	public List<ProductDetail> findBySupplierId(String supplierId);
	
	@Query("SELECT product FROM ProductDetail product WHERE product.supplierId = :supplierId AND product.categoryCode= :categoryCode")
	public List<ProductDetail> findSupplierIdAndCategoryCode(@Param("supplierId") String supplierId,@Param("categoryCode") String categoryCode);

}
