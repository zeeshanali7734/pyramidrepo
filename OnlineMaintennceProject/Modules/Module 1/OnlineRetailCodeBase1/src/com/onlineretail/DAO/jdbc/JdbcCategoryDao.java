package com.onlineretail.DAO.jdbc;

import com.onlineretail.DAO.CategoryDao;
import com.onlineretail.model.*;

import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.onlineretail.DAO.jdbc.ConnectionHelper.*;

public class JdbcCategoryDao implements CategoryDao {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JdbcCategoryDao.class);

	@Override
	public int Save(Category category) {
		Connection con = null;
		PreparedStatement stmt = null;
		int cnt = 0;
		try {
			con = getMySqlConnection();
			String query = "insert into category(cid,cname,description)"
					+ " values(category_seq.nextval,?,?)";
			stmt = con.prepareStatement(query);
			stmt.setString(1, category.getCname());
			stmt.setString(2, category.getDescription());
			cnt = stmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			// LOGGER.error(e.getMessage());
			e.printStackTrace();
		} finally {
			cleanup(con, stmt, null);
		}
		return cnt;
	}

	@Override
	public List<Category> findAll() {
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
	public int updateCategory(Category category) {
		Connection con = null;
		PreparedStatement stmt = null;
		int cnt = 0;
		try {
			con = getMySqlConnection();
			String query = "update category set description = ? where cid = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, category.getDescription());
			stmt.setInt(2, category.getCid());
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
	public void deleteCategory(int categoryId) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = getMySqlConnection();
			String query = "update category set status = 'n' where cid = ?";
			stmt = con.prepareStatement(query);
			stmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new RuntimeException("Category Not Deleted");
		} finally {
			cleanup(con, stmt, null);
		}
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
	public boolean isDuplicateCategoryName(String categoryName) {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt
					.executeQuery("select count(*) from category where cname = '"
							+ categoryName + "'");
			result.next();
			int count = result.getInt(1);
			con.close();
			if (count == 0)
				return false;
			else
				return true;
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

	@Override
	public List<Category> findCategory(String categoryName) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = con
					.prepareStatement("select * from category where cname = ?");
			stmt.setString(1, categoryName);
			result = stmt.executeQuery();
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
	public int findByCategoryName(String categoryName) {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		int count = 0;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt
					.executeQuery("select count(*) from category where cname = '"
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
}
