package com.prg.store.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.prg.store.domain.Order;
import com.prg.store.domain.OrderItem;

public interface OrderDao {

	void addOrder(Connection conn, Order order) throws SQLException;

	void addOrderItem(Connection conn, OrderItem item) throws SQLException;

}
