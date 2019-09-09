package com.onlineretail.DAO.jdbc;

import com.onlineretail.DAO.*;
import com.onlineretail.model.*;

import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.onlineretail.DAO.jdbc.ConnectionHelper.*;

public class JdbcProductDao implements ProductDao {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JdbcProductDao.class);

	@Override
	public List<Product> findAll() {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt.executeQuery("select * from product where status = 'y'");
			List<Product> products = new ArrayList<Product>();
			Product product;
			while (result.next()) {
				product = new Product();
				product.setPid(result.getInt("pid"));
				product.setPname(result.getString("productName"));
				product.setPdescription(result.getString("productDescription"));
				product.setCost(result.getDouble("cost"));
				product.setDiscount(result.getInt("discount"));
				product.setSid(result.getInt("sid"));
				product.setImage(result.getString("image"));
				product.setStatus(result.getString("status"));
				products.add(product);
			}
			con.close();
			return products;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, result);
		}
	}

	
	@Override
	public int Save(Product product) {
		Connection con = null;
		PreparedStatement stmt = null;
		int cnt = 0;
		try {
			con = getMySqlConnection();
			String query = "insert into product(pid,productName,productDescription,cost,discount,sid,image)"
					+ " values(Product_Seq.nextval,?,?,?,?,?,?)";
			stmt = con.prepareStatement(query);
			stmt.setString(1, product.getPname());
			stmt.setString(2, product.getPdescription());
			stmt.setDouble(3, product.getCost());
			stmt.setInt(4, product.getDiscount());
			stmt.setInt(5, product.getSid());
			stmt.setString(6, product.getImage());
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
	public void updateProduct(Product product) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = getMySqlConnection();
			String query = "update product set productDescription = ?, cost = ?,discount = ? where pid = ?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, product.getPdescription());
			stmt.setDouble(2, product.getCost());
			stmt.setInt(3, product.getDiscount());
			stmt.setInt(4, product.getPid());
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
	public void deleteProduct(int productId) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = getMySqlConnection();
			String query = "update product set status = 'n' where pid = ?";
			stmt = con.prepareStatement(query);
			stmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new RuntimeException("Product Not Deleted");
		} finally {
			cleanup(con, stmt, null);
		}
	}

	@Override
	public int findBySubCategoryId_Product(int subcategoryId) {
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
	public List<SubCategory> findAllSubCategory() {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt.executeQuery("select * from subcategory");
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
	public Product findByProductId(int productId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = con.prepareStatement("select * from product where pid = ?");
			stmt.setInt(1, productId);
			result = stmt.executeQuery();
			result.next();
			Product product = new Product();
			product.setPid(result.getInt("pid"));
			product.setPname(result.getString("productName"));
			product.setPdescription(result.getString("productDescription"));
			product.setCost(result.getDouble("cost"));
			product.setDiscount(result.getInt("discount"));
			product.setSid(result.getInt("sid"));
			product.setImage(result.getString("image"));
			product.setStatus(result.getString("status"));

			con.close();
			return product;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, result);
		}
	}

	@Override
	public Product findByProductName(String productName) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = con
					.prepareStatement("select * from product where productName = ?");
			stmt.setString(1, productName);
			result = stmt.executeQuery();
			result.next();
			Product product = new Product();
			product.setPid(result.getInt("pid"));
			product.setPname(result.getString("productName"));
			product.setPdescription(result.getString("productDescription"));
			product.setCost(result.getDouble("cost"));
			product.setDiscount(result.getInt("discount"));
			product.setSid(result.getInt("sid"));
			product.setImage(result.getString("image"));
			product.setStatus(result.getString("status"));
			con.close();
			return product;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, result);
		}
	}

	@Override
	public String findBySubCategoryId(int subcatgeoryId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = con
					.prepareStatement("select * from subcategory where sid = ?");
			stmt.setInt(1, subcatgeoryId);
			result = stmt.executeQuery();
			result.next();
			String subName = result.getString("subName");
			con.close();
			return subName;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, result);
		}
	}

	@Override
	public boolean isDuplicateProductName(String productName) {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt
					.executeQuery("select count(*) from product where productName = '"
							+ productName + "'");
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
	public List<Product> findProduct(String productName) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			con = getMySqlConnection();
			stmt = con
					.prepareStatement("select * from product where productName = ?");
			stmt.setString(1, productName);
			result = stmt.executeQuery();
			List<Product> products = new ArrayList<Product>();
			Product product;
			while (result.next()) {
				product = new Product();
				product.setPid(result.getInt("pid"));
				product.setPname(result.getString("productName"));
				product.setPdescription(result.getString("productDescription"));
				product.setCost(result.getDouble("cost"));
				product.setDiscount(result.getInt("discount"));
				product.setSid(result.getInt("sid"));
				product.setImage(result.getString("image"));
				product.setStatus(result.getString("status"));
				products.add(product);
			}
			con.close();
			return products;
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		} finally {
			cleanup(con, stmt, result);
		}
	}

	@Override
	public int findProductName(String productName) {
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		int count = 0;
		try {
			con = getMySqlConnection();
			stmt = (Statement) con.createStatement();
			result = stmt
					.executeQuery("select count(*) from product where productName = '"
							+ productName + "'");
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

	@Override
	public int findByProdId(int productId) {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		int status = 0;
		try {
			con = getMySqlConnection();
			stmt = con.prepareStatement("select count(*) from product where pid = ?");
			stmt.setInt(1, productId);
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

}
