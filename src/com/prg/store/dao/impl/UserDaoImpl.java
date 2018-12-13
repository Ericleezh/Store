package com.prg.store.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.prg.store.dao.UserDao;
import com.prg.store.domain.User;
import com.prg.store.utils.JDBCUtils;
import com.sun.xml.internal.bind.v2.TODO;

import enumeration.ActiveStatus;

public class UserDaoImpl implements UserDao {

	@Override
	public void userRegist(User user) throws SQLException {
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		runner.update(sql, params);
	}

	//TODO:这部分涉及到事务的处理，后期再做进一步优化
	@Override
	public User userActive(String active_code) throws SQLException {
		Connection conn = null;
		User user = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "select * from user where code = ? and state = ?";
			QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
			user = runner.query(sql, new BeanHandler<User>(User.class) , active_code, ActiveStatus.INACTIVATED.getValue());
			//激活成功后将激活码清空，防止长时间后再一次进行激活
			user.setCode(null);
			user.setState(ActiveStatus.ACTIVATED.getValue());
			String update_sql = "update user set username = ?,password = ?,name = ?,email = ?,telephone = ?,birthday = ?,sex = ?,state = ?,code = ? where uid =?";
			Object[] params = {user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};
			runner.update(update_sql, params);
			
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
		}
		
		return user;
	}

	
	@Override
	public User userLogin(User user) throws SQLException {
		String sql = "select * from user where username = ? and password = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());
	}

}
