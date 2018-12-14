package com.prg.store.service;

import java.util.List;

import com.prg.store.domain.Product;

public interface ProductService {

	List<Product> findHots() throws Exception;

	List<Product> findNews() throws Exception;

}
