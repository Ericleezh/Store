package com.prg.store.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.prg.store.dao.OrderDao;
import com.prg.store.dao.impl.OrderDaoImpl;
import com.prg.store.domain.Order;
import com.prg.store.domain.OrderItem;
import com.prg.store.domain.PageModel;
import com.prg.store.domain.User;
import com.prg.store.service.OrderService;
import com.prg.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void addOrder(Order order) throws SQLException {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			
			//保存订单
			OrderDao orderDao = new OrderDaoImpl();
			orderDao.addOrder(conn, order);
			
			//保存订单项
			for (OrderItem item : order.getList()) {
				orderDao.addOrderItem(conn, item);
			}
			
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public PageModel findMyOrdersWithPage(int current_page, User user) throws SQLException {
		OrderDao orderDao = new OrderDaoImpl();
		//获取该用户所有订单数
		int totalRecords = orderDao.getTotalRecords(user);
		PageModel pm = new PageModel(current_page, totalRecords, 3);
		
		//查询出所有相关订单
		List list = orderDao.findMyOrdersWithPage(user, pm.getStartIndex(), pm.getPageSize());
		//绑定集合
		pm.setList(list);
		pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
		
		return pm;
	}

}
