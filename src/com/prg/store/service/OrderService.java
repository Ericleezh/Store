package com.prg.store.service;

import java.sql.SQLException;

import com.prg.store.domain.Order;

public interface OrderService {

	void addOrder(Order order) throws SQLException;

}
