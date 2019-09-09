package com.onlineretail.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.onlineretail.DAO.*;
import com.onlineretail.DAO.jdbc.*;
import com.onlineretail.controller.*;
import com.onlineretail.model.*;

public class MainApp {

	static LoginController loginController;
	static SubCategoryController subCategoryController;
	static SubCategoryDao subCategoryDao;

	static Scanner scanner;
	static int choice = 0;
	static String message = null;

	public static void main(String[] args) {
		int result = 0;
		try {
			initialize();
			System.out
					.println("************************************************************************");
			System.out.println("				Online Retail								");
			System.out
					.println("************************************************************************");
			System.out.println("1. Login");
			System.out.println("2. Exit");
			System.out
					.println("----------------------------------------------------");
			System.out.println("Enter the Choice: ");
			choice = scanner.nextInt();

			switch (choice) {
			case 1:
				result = login();
				if (result == 1)
					adminOperation();
				else
					System.out.println("Invalid UserName");
				break;
			case 2:
				break;
			default:
				System.out.println("Invalid Choice");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initialize() {
		scanner = new Scanner(System.in);
		subCategoryController = new SubCategoryController();
		loginController = new LoginController();
		subCategoryDao = new JdbcSubCategoryDao();
	}

	public static int login() throws Exception {
		return loginController.loginValidate();
	}

	public static void adminOperation() {
		String ch = "y";
		int subChoice = 0;
		do {
			try {

				System.out.println("Main Menu");
				System.out
						.println("----------------------------------------------------");
				System.out.println("1. Sub Category");
				System.out.println("\t1. Add Sub Category ");
				System.out.println("\t2. View Sub Category ");
				System.out.println("\t3. View Based on Sub Category Name");
				System.out.println("\t4. Delete Sub Category ");
				System.out.println("\t5. Back ");

				System.out.println("Enter the Choice for Admin Operation: ");
				choice = scanner.nextInt();

				switch (choice) {
				case 1:
					System.out
							.println("Enter the Choice for Sub Category Operation: ");
					subChoice = scanner.nextInt();

					switch (subChoice) {
					case 1:
						message = subCategoryController.addSubCategory();
						System.out.println(message);
						scanner.nextLine();
						break;
					case 2:
						subCategoryController.findAll();
						scanner.nextLine();
						break;
					case 3:
						subCategoryController.findAll();
						scanner.nextLine();
						String subCategoryName;
						int status = 0;
						do {
							System.out.println("Enter Sub Category Name:  ");
							subCategoryName = scanner.nextLine();
							status = subCategoryController
									.findSubCategoryNameDetails(subCategoryName);
						} while (status == 0);
						if (status == 1) {
							subCategoryController
									.subCategoryNameDetails(subCategoryName);
						}
						break;
					case 4:
						subCategoryController.findAll();
						scanner.nextLine();
						int subCategoryId = 0;
						do {
							System.out.println("Enter Sub Category Id:  ");
							subCategoryId = scanner.nextInt();
							status = subCategoryDao.findBySubCategoryId(subCategoryId);
						} while (status == 0);
						if (status == 1) {
							subCategoryController.deleteSubCategory(subCategoryId);
							scanner.nextLine();
						}
						break;
					case 5:
						scanner.nextLine();
						break;
					default:
						System.out.println("Invalid Choice");
						scanner.nextLine();
						break;
					}
					break;
				default:
					System.out.println("Invalid Choice");
					scanner.nextLine();
					break;
				}
				System.out.println("Do you want to continue Y/N");
				ch = scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Invalid Choice");
				scanner.nextLine();
				System.out.println("Enter Valid Choice: ");
				ch = scanner.nextLine();
			}
			catch(RuntimeException e2){
				System.out.println(e2.getMessage());
			}
			catch (Exception e1) {

			}
		} while (ch.equals("Y") || ch.equals("y"));

	}
}
