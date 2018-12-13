package com.prg.store.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prg.store.domain.Category;
import com.prg.store.service.CategoryService;
import com.prg.store.service.impl.CategoryServiceImpl;
import com.prg.store.web.base.BaseServlet;

/**
 * 重定向到主页的servlet
 * @author PearRealGood
 *
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
//		CategoryService categoryService = new CategoryServiceImpl();
//		List<Category> list = categoryService.findAllCats();
//		
//		req.setAttribute("categorys", list);
		
		return "/jsp/index.jsp";
	}
	
}
