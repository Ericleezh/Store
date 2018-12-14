package com.prg.store.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringUtils;

import com.prg.store.dao.ProductDao;
import com.prg.store.domain.Product;
import com.prg.store.utils.JDBCUtils;
import com.sun.corba.se.spi.orb.StringPair;

public class ProductDaoImpl implements ProductDao{

	@Override
	public List<Product> findHots() throws Exception {
		String sql = "select * from product where is_hot = 1 and pflag = 0 order by pdate DESC limit 0 , 9";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> findNews() throws Exception{
		String sql = "select * from product where pflag = 0 order by pdate DESC limit 0,9";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public Product findProductById(String pid) throws Exception {
		String sql = "select * from product where pid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	@Override
	public int findTotalRecords(String cid) throws Exception {
		String sql = "select count(*) from product where cid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long)runner.query(sql, new ScalarHandler(),cid);
		return num.intValue();
	}

	@Override
	public List findProductsWithCidAndPage(String cid, int startIndex, int pageSize) throws Exception {
		String sql = "select * from product where cid = ? limit ?,?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Product>(Product.class), cid, startIndex, pageSize);
	}

	@Override
	public List<Product> findHistoryProducts(String[] ids) throws Exception {
		ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(ids));
		String all_id = "";
		if (arrayList.size() <= 7) {
			all_id = StringUtils.join(arrayList.toArray(), ",");
		} else {
			all_id = StringUtils.join(Arrays.copyOfRange(ids, 0, 7),",");
		}
		
		String sql = "select * from product where pid in ("+ all_id +") order by field(pid,"+all_id+")";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}

}
