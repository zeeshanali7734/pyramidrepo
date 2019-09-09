package com.onlineretail.controller;

import java.util.List;
import java.util.Scanner;

import com.onlineretail.DAO.*;
import com.onlineretail.DAO.jdbc.JdbcCategoryDao;
import com.onlineretail.model.*;
import com.onlineretail.service.*;
import com.onlineretail.service.impl.*;

public class CategoryController {
	public CategoryService categoryService;
	public CategoryDao categoryDao;
	public Scanner scanner;

	public CategoryController() {
		scanner = new Scanner(System.in);
		categoryDao = new JdbcCategoryDao();
		categoryService = new CategoryServiceImpl();
	}

	public String addCategory() throws Exception {
		try {
			System.out.println("Add Category");
			System.out
					.println("----------------------------------------------------");
			String Name, Description;
			System.out.println("Enter Category Name:  ");
			Name = scanner.nextLine();
			System.out.println("Enter Description:  ");
			Description = scanner.nextLine();
			if (categoryService.AddCategory(Name, Description) == 1) {
				return "Category Added";
			} else {
				return "Category Not Added";

			}
		} catch (Exception e) {
			return "Category Not Added";
		}
	}

	public void findAll() {
		List<Category> catgeories = categoryService.findAll();
		System.out.println("ID \tName\t\tDescription");
		for (Category category : catgeories) {
			if (category.getCname().length() > 0) {
				System.out.print(category.getCid() + "\t");
				System.out.print(category.getCname() + "\t\t");
				System.out.println(category.getDescription());
			}
		}
	}

	public int findCategoryNameDetails(String categoryName) {
		return categoryDao.findByCategoryName(categoryName);
	}

	public void categoryNameDetails(String categoryName) {
		List<Category> categories = categoryService.findCategory(categoryName);
		System.out.println("ID \tName\t\tDescription");
		for (Category category : categories) {
			System.out.print(category.getCid() + "\t");
			System.out.print("\t\t" + category.getDescription() + "\n");
		}
	}

	public void deleteCategory(int categoryId) {
		categoryService.deleteCategory(categoryId);
	}
}
