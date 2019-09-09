package com.onlineretail.service.impl;

import java.util.List;

import com.onlineretail.DAO.CategoryDao;
import com.onlineretail.DAO.jdbc.JdbcCategoryDao;
import com.onlineretail.model.Category;
import com.onlineretail.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	private CategoryDao categorydao;

	public CategoryServiceImpl() {
		categorydao = new JdbcCategoryDao();
	}

	@Override
	public int AddCategory(String Name, String Description) throws Exception {
		if (isDuplicateCategoryName(Name) == false) {
			Category category = null;
			category.setCname(Name);
			category.setDescription(Description);

			return categorydao.Save(category);
		} else {
			throw new Exception("Category Not Added");
		}
	}

	@Override
	public boolean isDuplicateCategoryName(String categoryName)
			throws Exception {
		boolean status = false;
		if (categorydao.isDuplicateCategoryName(categoryName) == true) {
			status = true;
			throw new Exception("Category Not Added");
		} else
			status = false;

		return status;
	}

	public List<Category> findAll() {
		return categorydao.findAll();
	}

	@Override
	public List<Category> findCategory(String categoryName) {
		return categorydao.findCategory(categoryName);
	}

	@Override
	public void deleteCategory(int categoryId) {
		categorydao.deleteCategory(categoryId);
	}

}
