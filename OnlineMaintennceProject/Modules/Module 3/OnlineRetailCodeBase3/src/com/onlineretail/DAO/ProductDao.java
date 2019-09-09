package com.onlineretail.DAO;

import com.onlineretail.model.*;

import java.util.*;

public interface ProductDao {
	 int Save(Product product);

	 List<Product> findAll();

	 void updateProduct(Product product);

	 void deleteProduct(int productId);

	 List<SubCategory> findAllSubCategory();

	 Product findByProductId(int productId);

	 Product findByProductName(String productName);

	String findBySubCategoryId(int subcatgeoryId);
	
	boolean isDuplicateProductName(String productName);
	
	List<Product> findProduct(String productName);
	
	int findProductName(String productName);

	int findByProdId(int productId);

	 int findBySubCategoryId_Product(int subcategoryId);
	
}