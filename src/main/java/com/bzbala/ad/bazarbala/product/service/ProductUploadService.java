package com.bzbala.ad.bazarbala.product.service;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;
import com.bzbala.ad.bazarbala.product.model.ProductClientRequest;
import com.bzbala.ad.bazarbala.product.model.ProductRequest;
import com.bzbala.ad.bazarbala.util.ProductUploadUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductUploadService {

	@Autowired
	ProductUploadUtil productUploadUtil;
	
	
	/**
	 * 
	 * @param productFile
	 * @return
	 * @throws BazarBalaDAOException
	 */
	public List<Map<String, String>> uploadProductFile(MultipartFile productFile) throws BazarBalaDAOException {

		try {

			Path tempDir = Files.createTempDirectory("");
			File tempfile = tempDir.resolve(productFile.getOriginalFilename()).toFile();
			productFile.transferTo(tempfile);
			Workbook workbook = WorkbookFactory.create(tempfile);

			Sheet sheet = workbook.getSheetAt(0);
			productUploadUtil.getRowStreamSupplier(sheet);
			Supplier<Stream<Row>> rowStream = productUploadUtil.getRowStreamSupplier(sheet);
			Row headerRow = rowStream.get().findFirst().get();

			List<String> headerCell = StreamSupport.stream(headerRow.spliterator(), false).map(Cell::getStringCellValue)
					.collect(Collectors.toList());

			int lineEnd = headerCell.size();
			DataFormatter formatter = new DataFormatter();
			return rowStream.get().skip(1).map(row -> {

				List<String> cellStream = StreamSupport.stream(row.spliterator(), false).map(cell ->{
					return formatter.formatCellValue(cell).trim();
					
				}).collect(Collectors.toList());

				return productUploadUtil.getCellStreamSupplier(lineEnd).get()
						.collect(Collectors.toMap(headerCell::get,cellStream::get));

				
			}).collect(Collectors.toList());

		} catch (IOException e) {
           throw new BazarBalaDAOException(e.getMessage(),"uploadProductFile",e.getCause());
			
		}
		
	}
	
	/**
	 * 
	 * @param uploadeProduct
	 */
	public ProductRequest getProductRequest(List<Map<String, String>> uploadeProduct) {
		
		
		List<ProductClientRequest> productlistInRequest = new ArrayList<>();
		ProductRequest productRequest = new ProductRequest();
		uploadeProduct.stream().forEach(item ->{
			ProductClientRequest request = new ProductClientRequest();
			 request.setProductCode(item.get("productCode"));
			request.setName(item.get("name"));
			request.setDescription(item.get("description"));
			request.setQuatityType(item.get("quantityType"));
		    request.setQuantity(Integer.parseInt(item.get("quantity")));
			request.setOrgin(item.get("orgin"));
			request.setBrand(item.get("brand"));
			request.setProductImage(item.get("productImage"));
			request.setStartDate(item.get("startDate"));
			request.setEndDate(item.get("endDate"));
			request.setGovApproved(item.get("govApproved"));
			request.setSellPrice(Double.parseDouble(item.get("sellPrice")));
			request.setBasePrice(Double.parseDouble(item.get("basePrice")));
			request.setDiscount(item.get("discount"));
			request.setDiscountStartDate(item.get("discountStartDate"));
			request.setDiscountEndDate(item.get("discountEndDate"));
			request.setCategoryCode(item.get("categoryCode"));
			request.setCategoryName(item.get("categoryName"));
			request.setCategoryDescription(item.get("categoryDescription"));
			request.setCategoryImageId(item.get("categoryImageId"));
			productlistInRequest.add(request);
		
		});
		
		productRequest.setProductRequesst(productlistInRequest);
		
		return productRequest;
		
	}

}
