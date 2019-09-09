package com.onlineretail.service;

import java.util.List;

import com.onlineretail.model.*;

public interface SubCategoryService {

	int AddSubCategory(String Name, String Description, int CategoryID)
			throws Exception;

	boolean isDuplicateSubCategoryName(String subCategoryName) throws Exception;

	List<SubCategory> findAll();
	
	List<Category> findCategoryAll();

	List<SubCategory> findSubCategory(String subCategoryName);
	
    void deleteSubCategory(int subCategoryId);

}
