package com.onlineretail.DAO.jdbc;

import com.onlineretail.DAO.*;
import com.onlineretail.model.*;

import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.onlineretail.DAO.jdbc.ConnectionHelper.*;

public class JdbcSubCategoryDao implements SubCategoryDao {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JdbcSubCategoryDao.class);

	@Override
	public int Save(SubCategory subcategory) {
		Connection con = null;
		PreparedStatement stmt = null;
		int cnt = 0;
		try {
			con = getMySqlConnection();
			String query = "insert into subcategory(sid,subname,subdescription,cid)"
					+ " values(sub_category_seq.nextval,?,?,?)";
			stmt = con.prepareStatement(query);
			stmt.setString(1, subcategory.getSubcname());
			stmt.setString(2, subcategory.getSdescription());
			stmt.setInt(3, subcategory.getCid());
			cnt = stmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			System.out.println(e.getMessage());
		} finally {
			cleanup(con, stmt, null);
		}
		return cnt;
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
	public List<SubCategory> findAll() {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt.executeQuery("select * from subcategory where status = 'y'");
			List<SubCategory> subcategories = new ArrayList<SubCategory>();
			SubCategory subcategory;
			while (result.next()) {
				subcategory = new SubCategory();
				subcategory.setSid(result.getInt("sid"));
				subcategory.setSubcname(result.getString("subName"));
				subcategory.setSdescription(result.getString("subDescription"));
				subcategory.setCid(result.getInt("cid"));
				subcategory.setStatus(result.getString("status"));
				subcategories.add(subcategory);
			}
			con.close();
			return subcategories;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, result);
		}
	}

	@Override
	public void updateSubCategory(SubCategory subcategory) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = getMySqlConnection();
			String query = "update subCategory set subDescription = ? where sid = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, subcategory.getSdescription());
			stmt.setInt(2, subcategory.getSid());
			stmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, null);
		}
	}

	@Override
	public void deleteSubCategory(int subcategoryId) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = getMySqlConnection();
			String query = "update subcategory set status = 'n' where sid = ";
			stmt = con.prepareStatement(query);
			stmt.setInt(1, subcategoryId);
			con.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new RuntimeException("Sub Category Not Deleted");
		} finally {
			cleanup(con, stmt, null);
		}
	}

	@Override
	public List<Category> findAllCategory() {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt.executeQuery("select * from category");
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
	public int findBySubCategoryId(int subcategoryId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		int status = 0;
		try {
			con = getMySqlConnection();
			stmt = con.prepareStatement("select count(*) from subcategory where sid = ?");
			stmt.setInt(1, subcategoryId);
			result = stmt.executeQuery();
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
	public boolean isDuplicateSubCategoryName(String subCategoryName) {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt.executeQuery("select count(*) from subcategory where subName = '"
					+ subCategoryName + "'");
			result.next();
			int count = result.getInt(1);
			con.close();
			if(count == 0)
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
	public List<SubCategory> findSubCategory(String subCategoryName) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = con
					.prepareStatement("select * from subcategory where subName = ?");
			stmt.setString(1, subCategoryName);
			result = stmt.executeQuery();
			List<SubCategory> subcategories = new ArrayList<SubCategory>();
			SubCategory subcategory;
			while (result.next()) {
				subcategory = new SubCategory();
				subcategory.setSid(result.getInt("sid"));
				subcategory.setSubcname(result.getString("subName"));
				subcategory.setSdescription(result.getString("subDescription"));
				subcategory.setCid(result.getInt("cid"));
				subcategory.setStatus(result.getString("status"));
				subcategories.add(subcategory);
			}
			con.close();
			return subcategories;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, result);
		}
	}

	@Override
	public int findBySubCategoryName(String subCategoryName) {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		int count = 0;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt
					.executeQuery("select count(*) from subcategory where subName = '"
							+ subCategoryName + "'");
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

}
