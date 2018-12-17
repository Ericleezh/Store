package com.prg.store.service.impl;

import java.sql.SQLException;

import com.prg.store.dao.CategoryDao;
import com.prg.store.dao.UserDao;
import com.prg.store.dao.impl.UserDaoImpl;
import com.prg.store.domain.User;
import com.prg.store.service.UserService;
import com.prg.store.utils.BeanFactory;

public class UserServiceImpl implements UserService{
	UserDao userDao = (UserDao) BeanFactory.createObject("UserDao");
	
	@Override
	public void userRegist(User user) throws SQLException {
		userDao.userRegist(user);
	}

	@Override
	public Boolean userActive(String active_code) throws SQLException {
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
		return userDao.userLogin(user);
	}

	@Override
	public Boolean checkNameExist(String username) throws SQLException {
		return userDao.checkNameExist(username);
	}
}
