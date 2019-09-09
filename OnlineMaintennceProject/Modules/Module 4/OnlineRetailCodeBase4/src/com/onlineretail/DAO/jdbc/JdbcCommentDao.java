package com.onlineretail.DAO.jdbc;

import com.onlineretail.DAO.*;
import com.onlineretail.model.*;

import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.onlineretail.DAO.jdbc.ConnectionHelper.*;

public class JdbcCommentDao implements CommentDao {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JdbcCommentDao.class);

	@Override
	public int Save(Comment comment) {
		Connection con = null;
		PreparedStatement stmt = null;
		int cnt = 0;
		try {
			con = getMySqlConnection();
			String query = "insert into comment(id,categoryname,comments,commentdate,rating)"
					+ " values(Comment_seq.nextval,?,?,?,?)";
			stmt = con.prepareStatement(query);
			stmt.setString(1, comment.getCategoryname());
			stmt.setString(2, comment.getComment());
			stmt.setDate(3, (java.sql.Date) comment.getDate());
			stmt.setInt(4, comment.getRating());
			cnt = stmt.executeUpdate();
			con.close();
			return cnt;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, null);
		}
	}


	@Override
	public List<Comment> findAll() {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt.executeQuery("select * from comment where status = 'y'");
			List<Comment> comments = new ArrayList<Comment>();
			Comment comment;
			while (result.next()) {
				comment = new Comment();
				comment.setId(result.getInt("id"));
				comment.setCategoryname(result.getString("categoryname"));
				comment.setComment(result.getString("comments"));
				comment.setDate(result.getDate("commentdate"));
				comment.setRating(result.getInt("rating"));
				comment.setStatus(result.getString("status"));
				comments.add(comment);
			}
			con.close();
			return comments;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, result);
		}
	}

	@Override
	public List<Category> findCategoryAll() {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt
					.executeQuery("select * from category where status = 'y'");
			List<Category> categories = new ArrayList<Category>();
			Category category;
			while (result.next()) {
				category = new Category();
				category.setCid(result.getInt("cid"));
				category.setCname(result.getString("cname"));
				category.setDescription(result.getString("description"));
				category.setStatus(result.getString("status"));
				categories.add(category);
			}
			con.close();
			return categories;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, result);
		}
	}


	@Override
	public List<Comment> findComment(String categoryName) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = con
					.prepareStatement("select * from comment where CategoryName = ?");
			stmt.setString(1, categoryName);
			result = stmt.executeQuery();
			List<Comment> comments = new ArrayList<Comment>();
			Comment comment;
			while (result.next()) {
				comment = new Comment();
				comment.setId(result.getInt("id"));
				comment.setCategoryname(result.getString("categoryname"));
				comment.setComment(result.getString("comments"));
				comment.setDate(result.getDate("commentdate"));
				comment.setRating(result.getInt("rating"));
				comment.setStatus(result.getString("status"));
				comments.add(comment);
			}
			con.close();
			return comments;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, result);
		}
	}


	@Override
	public int findByComment(String categoryName) {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		int count = 0;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt
					.executeQuery("select count(*) from comment where CategoryName = '"
							+ categoryName + "'");
			result.next();
			count = result.getInt(1);
			con.close();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, result);
		}
		return count;
	}


	public int findByCategoryId(int categoryId) {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		int status = 0;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt
					.executeQuery("select count(*) from category where cid = "
							+ categoryId);
			result.next();
			status = result.getInt(1);
			con.close();
			return status;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, result);
		}
	}


	@Override
	public String getCategoryName(int categoryId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = con.prepareStatement("select * from category where cid = ?");
			stmt.setInt(1, categoryId);
			result = stmt.executeQuery();
			result.next();
			String categoryName = result.getString("cname");
			con.close();
			return categoryName;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, result);
		}
	}

}
