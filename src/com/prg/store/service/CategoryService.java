package com.prg.store.service;

import java.util.List;

import com.prg.store.domain.Category;

public interface CategoryService {

	List<Category> findAllCats() throws Exception;

}
