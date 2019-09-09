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
	static ProductController productController;
	static ProductDao productDao;

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
		loginController = new LoginController();
		productController = new ProductController();
		productDao = new JdbcProductDao();
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
				System.out.println("1. Product");
				System.out.println("\t1. Add Product ");
				System.out.println("\t2. View Product ");
				System.out.println("\t3. View Based on Product Name");
				System.out.println("\t4. Delete Product ");
				System.out.println("\t5. Back ");

				System.out.println("Enter the Choice for Admin Operation: ");
				choice = scanner.nextInt();

				switch (choice) {
				case 1:
					System.out
							.println("Enter the Choice for Product Operation: ");
					subChoice = scanner.nextInt();

					switch (subChoice) {
					case 1:
						message = productController.addProduct();
						System.out.println(message);
						scanner.nextLine();
						break;
					case 2:
						productController.findAll();
						scanner.nextLine();
						break;
					case 3:
						productController.findAll();
						scanner.nextLine();
						String productName;
						int status = 0;
						do {
							System.out.println("Enter Product Name:  ");
							productName = scanner.nextLine();
							status = productController
									.findProductNameDetails(productName);
						} while (status == 0);
						if (status == 1) {
							productController.ProductNameDetails(productName);
						}
						break;
					case 4:
						productController.findAll();
						int productId = 0;
						scanner.nextLine();
						do {
							System.out.println("Enter Product Id:  ");
							productId = scanner.nextInt();
							status = productDao.findByProdId(productId);
						} while (status == 0);
						if (status == 1) {
							productController.deleteProduct(productId);
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
