package com.prg.store.service;

import java.util.List;

import com.prg.store.domain.PageModel;
import com.prg.store.domain.Product;

public interface ProductService {

	List<Product> findHots() throws Exception;

	List<Product> findNews() throws Exception;

	Product findProductById(String pid) throws Exception;

	PageModel findProductsWithCidAndPage(String cid, int current_page) throws Exception;

}
