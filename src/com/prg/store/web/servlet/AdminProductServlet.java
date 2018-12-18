package com.prg.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prg.store.domain.PageModel;
import com.prg.store.service.ProductService;
import com.prg.store.service.impl.ProductServiceImpl;
import com.prg.store.web.base.BaseServlet;

/**
 * Servlet implementation class AdminProductServlet
 */
@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	ProductService productService = new ProductServiceImpl();
	
	public String findAllPros(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int current_page = Integer.parseInt(req.getParameter("page"));
		PageModel pm = productService.findAllProductsWithPage(current_page);
		req.setAttribute("page", pm);
		
		return "/admin/product/list.jsp";
	}
}
