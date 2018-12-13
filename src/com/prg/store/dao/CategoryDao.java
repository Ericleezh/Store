package com.prg.store.dao;

import java.util.List;

import com.prg.store.domain.Category;

public interface CategoryDao {

	List<Category> findAllCats() throws Exception;

}
