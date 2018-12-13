package com.prg.store.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.prg.store.dao.CategoryDao;
import com.prg.store.domain.Category;
import com.prg.store.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao{

	@Override
	public List<Category> findAllCats() throws Exception {
		String sql = "select * from category";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}

}
