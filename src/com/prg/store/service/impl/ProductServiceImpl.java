package com.prg.store.service.impl;

import java.util.List;

import com.prg.store.dao.ProductDao;
import com.prg.store.dao.impl.ProductDaoImpl;
import com.prg.store.domain.Product;
import com.prg.store.service.ProductService;

public class ProductServiceImpl implements ProductService {
	ProductDao productDao = new ProductDaoImpl();
	
	@Override
	public List<Product> findHots() throws Exception {
		return productDao.findHots();
	}

	@Override
	public List<Product> findNews() throws Exception {
		return productDao.findNews();
	}

}
