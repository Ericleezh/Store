package com.prg.store.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
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

	@Override
	public Order findOrderByOId(String order_id) throws SQLException {
		//獲取order對象
		String sql = "select * from orders where oid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Order order = runner.query(sql, new BeanHandler<Order>(Order.class), order_id);
		
		//獲取order下的訂單項以及product
		sql = "select * from orderitem o, product p where o.pid = p.pid and o.oid = ?";
		List<Map<String, Object>> list = runner.query(sql, new MapListHandler(), order_id);
		for (Map<String, Object> map : list) {
			OrderItem orderItem = new OrderItem();
			Product product = new Product();
			
			MyBeanUtils.populate02(orderItem, map);
			MyBeanUtils.populate02(product, map);
			orderItem.setProduct(product);
			
			order.getList().add(orderItem);
		}
		return order;
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		String sql = "update orders set ordertime=?,total=?,state=?,address=?,name=?,telephone=? where oid=?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {order.getOrdertime(), order.getTotal(), order.getState(), order.getAddress(), order.getName(), order.getTelephone(), order.getOid()};
		runner.update(sql, params);
	}

}
