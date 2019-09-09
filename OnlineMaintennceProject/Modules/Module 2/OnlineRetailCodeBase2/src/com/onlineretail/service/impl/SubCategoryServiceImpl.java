package com.onlineretail.service.impl;

import java.util.List;

import com.onlineretail.DAO.*;
import com.onlineretail.DAO.jdbc.*;
import com.onlineretail.model.*;
import com.onlineretail.service.*;

public class SubCategoryServiceImpl implements SubCategoryService {

	private SubCategoryDao subcategorydao;

	public SubCategoryServiceImpl() {
		subcategorydao = new JdbcSubCategoryDao();
	}

	public int AddSubCategory(String Name, String Description, int CategoryID) throws Exception 
	{
		if (isDuplicateSubCategoryName(Name) == false) {
			SubCategory subCategory = null;
			subCategory.setSubcname(Name);
			subCategory.setSdescription(Description);
			subCategory.setCid(CategoryID);
			
			return subcategorydao.Save(subCategory);
		} else {
			throw new Exception("Sub Category Not Added");
		}
	}

	public boolean isDuplicateSubCategoryName(String subCategoryName) throws Exception {
		boolean status = false;
		 if(subcategorydao.isDuplicateSubCategoryName(subCategoryName) == true)
		 {	 
			 status = true;
			 throw new Exception("Sub Category Not Added");
		 }
		 else
			 status = false;
		 
		 return status;
	}
	
	public List<SubCategory> findAll() {
		return subcategorydao.findAll();
	}

	public List<SubCategory> findSubCategory(String subCategoryName) {
		return subcategorydao.findSubCategory(subCategoryName);
	}	

	public void deleteSubCategory(int subCategoryId) {
		subcategorydao.deleteSubCategory(subCategoryId);
	}
	
	public List<Category> findCategoryAll() {
		return subcategorydao.findCategoryAll();
	}

}