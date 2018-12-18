package com.prg.store.dao;

import java.util.List;

import com.prg.store.domain.Category;
import com.prg.store.domain.Product;

public interface ProductDao {

	List<Product> findHots() throws Exception;

	List<Product> findNews() throws Exception;

	Product findProductById(String pid) throws Exception;

	int findTotalRecords(String cid) throws Exception;

	List findProductsWithCidAndPage(String cid, int startIndex, int pageSize) throws Exception;

	List<Product> findHistoryProducts(String[] ids) throws Exception;

	void delProduct(Category category) throws Exception;

	int findTotalRecordsByQuery();

	List findAllProductsWithPage(int startIndex, int pageSize);

}
