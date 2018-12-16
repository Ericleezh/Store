package com.prg.store.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.prg.store.domain.Order;
import com.prg.store.domain.OrderItem;
import com.prg.store.domain.User;

public interface OrderDao {

	void addOrder(Connection conn, Order order) throws SQLException;

	void addOrderItem(Connection conn, OrderItem item) throws SQLException;

	int getTotalRecords(User user) throws SQLException;

	List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws SQLException;

	Order findOrderByOId(String order_id) throws SQLException;

	void updateOrder(Order order) throws Exception;

}
