package com.onlineretail.service;

import java.util.List;

import com.onlineretail.model.Category;

public interface CategoryService {

	int AddCategory(String Name,String Description) throws Exception;
		
	 boolean isDuplicateCategoryName(String categoryName) throws Exception;

	 List<Category> findAll();
	 
	 List<Category> findCategory(String categoryName);
	 
	 void deleteCategory(int categoryId);

}
