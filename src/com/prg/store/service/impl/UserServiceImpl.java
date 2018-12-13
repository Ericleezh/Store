package com.prg.store.service.impl;

import java.sql.SQLException;

import com.prg.store.dao.UserDao;
import com.prg.store.dao.impl.UserDaoImpl;
import com.prg.store.domain.User;
import com.prg.store.service.UserService;

public class UserServiceImpl implements UserService{

	@Override
	public void userRegist(User user) throws SQLException {
		UserDao userDao = new UserDaoImpl();
		userDao.userRegist(user);
	}

	@Override
	public Boolean userActive(String active_code) throws SQLException {
		UserDao userDao = new UserDaoImpl();
		User user = userDao.userActive(active_code);
		//注册成功
		if (user != null) {
			return true;
		}
		//注册失败
		return false;
	}

	@Override
	public User userLogin(User user) throws SQLException {
		UserDao userDao = new UserDaoImpl();
		return userDao.userLogin(user);
	}

	@Override
	public Boolean checkNameExist(String username) throws SQLException {
		UserDao userDao = new UserDaoImpl();

		return userDao.checkNameExist(username);
	}
}
