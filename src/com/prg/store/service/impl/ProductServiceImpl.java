package com.prg.store.service.impl;

import java.util.List;

import com.prg.store.dao.ProductDao;
import com.prg.store.dao.impl.ProductDaoImpl;
import com.prg.store.domain.PageModel;
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

	@Override
	public Product findProductById(String pid) throws Exception {
		return productDao.findProductById(pid);
	}

	@Override
	public PageModel findProductsWithCidAndPage(String cid, int current_page) throws Exception {
		
		int totalRecords = productDao.findTotalRecords(cid);
		PageModel pm = new PageModel(current_page, totalRecords, 12);
		
		List list = productDao.findProductsWithCidAndPage(cid, pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		
		pm.setUrl("ProductServlet?method=findProductsWithCidAndPage&cid="+cid);
		return pm;
	}

	@Override
	public List<Product> findHistoryProducts(String[] ids) throws Exception {
		return productDao.findHistoryProducts(ids);
	}

}
