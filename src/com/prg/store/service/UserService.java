package com.prg.store.service;

import java.sql.SQLException;

import com.prg.store.domain.User;

public interface UserService {

	void userRegist(User user) throws SQLException;

	Boolean userActive(String active_code) throws SQLException;

	User userLogin(User user) throws SQLException;

	Boolean checkNameExist(String username) throws SQLException;

	User login(String username, String password);

}
