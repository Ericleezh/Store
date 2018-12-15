package com.prg.store.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.prg.store.dao.OrderDao;
import com.prg.store.domain.Order;
import com.prg.store.domain.OrderItem;

public class OrderDaoImpl implements OrderDao{

	@Override
	public void addOrder(Connection conn, Order order) throws SQLException {
		String sql = "insert into orders values (?,?,?,?,?,?,?,?)";
		QueryRunner runner = new QueryRunner();
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
		runner.update(conn, sql, params);
	}

	@Override
	public void addOrderItem(Connection conn , OrderItem item) throws SQLException {
		String sql = "insert into orderitem values (?,?,?,?,?)";
		QueryRunner runner = new QueryRunner();
		Object[] params = {item.getItemid(),item.getQuantity(),item.getTotal(),item.getProduct().getPid(),item.getOrder().getOid()};
		runner.update(conn, sql, params);
	}

}
