package com.onlineretail.service.impl;


import com.onlineretail.DAO.*;
import com.onlineretail.DAO.jdbc.*;
import com.onlineretail.service.*;

public class LoginServiceImpl implements LoginService {

	private LoginDao logindao;

	public LoginServiceImpl() {
		logindao = new JdbcLoginDao();
	}

	public int loginValidate(String username, String password) {
	
		return logindao.loginValidate(username, password);
		
	}

}
