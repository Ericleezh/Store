package com.prg.store.service;

import java.sql.SQLException;

import com.prg.store.domain.Order;
import com.prg.store.domain.PageModel;
import com.prg.store.domain.User;

public interface OrderService {

	void addOrder(Order order) throws SQLException;

	PageModel findMyOrdersWithPage(int current_page, User user) throws SQLException;

	Order findOrderByOId(String order_id) throws SQLException;

	void updateOrder(Order order) throws Exception;

}
