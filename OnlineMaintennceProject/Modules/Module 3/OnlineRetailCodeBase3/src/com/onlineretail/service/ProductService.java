package com.onlineretail.service;

import java.util.List;

import com.onlineretail.model.*;

public interface ProductService {

	int AddProduct(String Name,String Description,double Cost,int Discount,int SubCategoryID,String Image) throws Exception;
	
	boolean isDuplicateProductName(String productName) throws Exception;
	
	List<Product> findAll();
	
	List<Product> findProduct(String productName);
	
	void deleteProduct(int productId);

	List<SubCategory> findSubCategoryAll();

}
