package com.prg.store.web.servlet;

import com.prg.store.domain.Product;
import com.prg.store.service.ProductService;
import com.prg.store.service.impl.ProductServiceImpl;
import com.prg.store.web.base.BaseServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProductServlet")
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String productInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		String pid = req.getParameter("pid");
		ProductService productService = new ProductServiceImpl();
		Product product= productService.findProductById(pid);
		
		req.setAttribute("product", product);
		
		return "/jsp/product_info.jsp";
	}
}