package com.onlineretail.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.onlineretail.DAO.*;
import com.onlineretail.DAO.jdbc.*;
import com.onlineretail.model.*;
import com.onlineretail.service.*;
import com.onlineretail.service.impl.*;

public class CommentController {
	public CommentService commentService;
	public CommentDao commentDao;
	public Scanner scanner;

	public CommentController() {
		scanner = new Scanner(System.in);
		commentService = new CommentServiceImpl();
		commentDao = new JdbcCommentDao();
	}

	public String addComment() throws Exception {
		String result = null;
		try {
			System.out.println("Add Comments");
			System.out
					.println("----------------------------------------------------");
			String comment, categoryName;
			int rating, categoryId, status;
			findCategoryAll();
			do {
				System.out.println("Enter Category ID:  ");
				categoryId = scanner.nextInt();
				status = commentDao.findByCategoryId(categoryId);
			} while (status == 0);
			categoryName = commentDao.getCategoryName(categoryId);
			scanner.nextLine();
			System.out.println("Enter Comment:  ");
			comment = scanner.nextLine();
			System.out.println("Enter Rating:  ");
			rating = scanner.nextInt();
			java.util.Date dt = new java.util.Date();
			Date commentDate = new Date(dt.getYear(), dt.getMonth(),
					dt.getDate());

			if (status == 1) {
				if (commentService.AddComment(comment, categoryName,
						commentDate, rating) == 1) {
					result = "Comment Added";
				} else {
					result = "Comment Not Added";
				}
			}
		} catch (Exception e) {
			result = "Comment Not Added";
		}
		return result;

	}

	public void findAll() {
		List<Comment> comments = commentService.findAllComments();
		System.out
				.println("ID \t Category Name\t\tComments \t\tComment Date\t\tRating");
		for (Comment comment : comments) {
			if (comment.getComment().length() > 0) {
				System.out.println(comment.getId() + "\t"
						+ comment.getCategoryname() + "\t\t"
						+ comment.getComment() + "\t\t" + comment.getDate()
						+ "\t\t" + comment.getRating());
			}
		}
	}

	public int findCommentDetails(String categoryName) {
		return commentDao.findByComment(categoryName);
	}

	public void CommentDetails(String categoryName) {
		List<Comment> comments = commentService.findComment(categoryName);
		System.out
				.println("ID \t Category Name\t\tComments \t\tComment Date\t\tRating");
		for (Comment comment : comments) {
			System.out.println(comment.getId() + "\t"
					+ comment.getCategoryname() + "\t\t" + comment.getDate() 
					+ "\t\t");
		}
	}

	public void findCategoryAll() {
		List<Category> catgeories = commentService.findCategoryAll();
		System.out.println("ID \tName\t\tDescription");
		for (Category category : catgeories) {
			if (category.getCname().length() > 0) {
				System.out.print(category.getCid() + "\t");
				System.out.print(category.getCname() + "\t\t");
				System.out.println(category.getDescription());
			}
		}
	}

}
