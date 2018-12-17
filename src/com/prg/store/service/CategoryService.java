package com.prg.store.service;

import java.util.List;

import com.prg.store.domain.Category;

public interface CategoryService {

	List<Category> findAllCats() throws Exception;

	void updateCategory(Category category) throws Exception;

	Category findCategoryById(String cid) throws Exception;

	void addCategory(Category category) throws Exception;

	void delCategory(Category category) throws Exception;

}
