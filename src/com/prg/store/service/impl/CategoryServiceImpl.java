package com.prg.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.prg.store.dao.CategoryDao;
import com.prg.store.dao.impl.CategoryDaoImpl;
import com.prg.store.domain.Category;
import com.prg.store.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public List<Category> findAllCats() throws Exception {
		CategoryDao categoryDao = new CategoryDaoImpl();
		return categoryDao.findAllCats();
	}

}
