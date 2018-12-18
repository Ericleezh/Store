package com.prg.store.dao;

import java.sql.SQLException;

import com.prg.store.domain.User;

public interface UserDao {

	void userRegist(User user) throws SQLException;

	User userActive(String active_code) throws SQLException;

	User userLogin(User user) throws SQLException;

	Boolean checkNameExist(String username) throws SQLException;

	User login(String username, String password);

}
