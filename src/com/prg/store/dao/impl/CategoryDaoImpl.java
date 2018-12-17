package com.prg.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.prg.store.dao.CategoryDao;
import com.prg.store.domain.Category;
import com.prg.store.domain.Product;
import com.prg.store.utils.JDBCUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class CategoryDaoImpl implements CategoryDao{

	@Override
	public List<Category> findAllCats() throws Exception {
		String sql = "select * from category";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}

	@Override
	public void updateCategory(Category category) throws Exception{
		String sql = "update category set cname = ? where cid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {category.getCname(),category.getCid()};
		runner.update(sql, params);
	}

	@Override
	public Category findCategoryById(String cid) throws Exception{
		String sql = "select * from category where cid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanHandler<Category>(Category.class), cid);
	}

	@Override
	public void addCategory(Category category) throws Exception {
		String sql = "insert into category values(?,?)";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		runner.update(sql, category.getCid(), category.getCname());
	}

	@Override
	public void delCategory(Category category) throws Exception{
		String sql = "delete from category where cid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		runner.update(sql, category.getCid());
	}

}
