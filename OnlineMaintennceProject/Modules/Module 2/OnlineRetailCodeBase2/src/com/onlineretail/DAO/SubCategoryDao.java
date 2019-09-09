package com.onlineretail.DAO;

import java.util.*;

import com.onlineretail.model.*;

public interface SubCategoryDao {
	int Save(SubCategory subcategory);

	List<SubCategory> findAll();

	void updateSubCategory(SubCategory subcategory);

	void deleteSubCategory(int subcategoryId);

	List<Category> findAllCategory();
	
	 int findBySubCategoryId(int subcategoryId);
	
	 boolean isDuplicateSubCategoryName(String subCategoryName);

	 List<SubCategory> findSubCategory(String subCategoryName);
	 
	 int findBySubCategoryName(String subCategoryName);
	 
	 List<Category> findCategoryAll();
	 
	 int findByCategoryId(int categoryId);


}