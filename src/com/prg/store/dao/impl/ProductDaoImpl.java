package com.prg.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.prg.store.dao.ProductDao;
import com.prg.store.domain.Product;
import com.prg.store.utils.JDBCUtils;

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

}
