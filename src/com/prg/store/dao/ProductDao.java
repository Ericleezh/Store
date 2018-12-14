package com.prg.store.dao;

import java.util.List;

import com.prg.store.domain.Product;

public interface ProductDao {

	List<Product> findHots() throws Exception;

	List<Product> findNews() throws Exception;

}
