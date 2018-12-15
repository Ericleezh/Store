package com.prg.store.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.prg.store.dao.OrderDao;
import com.prg.store.domain.Order;
import com.prg.store.domain.OrderItem;
import com.prg.store.domain.Product;
import com.prg.store.domain.User;
import com.prg.store.utils.JDBCUtils;
import com.prg.store.utils.MyBeanUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

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

	@Override
	public int getTotalRecords(User user) throws SQLException {
		String sql = "select count(*) from orders where uid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Long count = (Long)runner.query(sql, new ScalarHandler(), user.getUid());
		return count.intValue();
	}

	@Override
	public List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws SQLException {
		String sql = "select * from orders where uid = ? limit ?,?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = runner.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(),startIndex,pageSize);
		for (Order order : list) {
			sql = "select * from orderitem o, product p where o.pid=p.pid and o.oid=?";
			List<Map<String, Object>> l = runner.query(sql, new MapListHandler(), order.getOid());
			for (Map<String, Object> map : l) {
				Product product = new Product();
				OrderItem orderItem = new OrderItem();
				
				MyBeanUtils.populate02(product, map);
				MyBeanUtils.populate02(orderItem, map);
				
				orderItem.setProduct(product);
				order.getList().add(orderItem);
				
			}
		}
		return list;
	}

}
