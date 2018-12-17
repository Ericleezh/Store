package com.prg.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.prg.store.dao.CategoryDao;
import com.prg.store.dao.impl.CategoryDaoImpl;
import com.prg.store.domain.Category;
import com.prg.store.service.CategoryService;
import com.prg.store.service.ProductService;
import com.prg.store.utils.JDBCUtils;
import com.prg.store.utils.JedisUtils;

import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {
	CategoryDao categoryDao = new CategoryDaoImpl();
	
	@Override
	public List<Category> findAllCats() throws Exception {
		return categoryDao.findAllCats();
	}

	@Override
	public void updateCategory(Category category) throws Exception {
		categoryDao.updateCategory(category);
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("all_cats");
		JedisUtils.closeJedis(jedis);
	}

	@Override
	public Category findCategoryById(String cid) throws Exception {
		return categoryDao.findCategoryById(cid);
	}

	@Override
	public void addCategory(Category category) throws Exception {
		categoryDao.addCategory(category);
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("all_cats");
		JedisUtils.closeJedis(jedis);		
	}

	@Override
	public void delCategory(Category category) throws Exception {
		try {
			JDBCUtils.startTransaction();
			//软删除 商品
			Jedis jedis = JedisUtils.getJedis();
			jedis.del("all_cats");
			JedisUtils.closeJedis(jedis);	
			ProductService productService = new ProductServiceImpl();
			productService.delProduct(category);
			categoryDao.delCategory(category);
			System.out.println("删除成功");
			JDBCUtils.commitAndClose();
		} catch (Exception e) {
			System.out.println("删除失败");
			e.printStackTrace();
			JDBCUtils.rollbackAndClose();
		}
	}

}
