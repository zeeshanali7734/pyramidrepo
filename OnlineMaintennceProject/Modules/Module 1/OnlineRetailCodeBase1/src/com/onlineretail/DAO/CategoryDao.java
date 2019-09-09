package com.onlineretail.DAO;

import java.util.List;

import com.onlineretail.model.Category;

public interface CategoryDao {
	 int Save(Category category);

	 List<Category> findAll();

	 int findByCategoryId(int categoryId);
	
	 String getCategoryName(int categoryId);
	 
	 boolean isDuplicateCategoryName(String categoryName);
	
	 int updateCategory(Category category);

	 void deleteCategory(int categoryId);
	 
	 List<Category> findCategory(String categoryName);
	 
	 int findByCategoryName(String categoryName);
}