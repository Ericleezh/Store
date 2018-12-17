package com.prg.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prg.store.domain.Category;
import com.prg.store.service.CategoryService;
import com.prg.store.service.impl.CategoryServiceImpl;
import com.prg.store.utils.UUIDUtils;
import com.prg.store.web.base.BaseServlet;

@WebServlet("/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	CategoryService categoryService = new CategoryServiceImpl();
	/**
	 * 獲取所有的類別
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<Category> list = categoryService.findAllCats();
		req.setAttribute("list", list);
		return "/admin/category/list.jsp";
	}

	/**
	 * 跳轉到編輯界面
	 */
	public String editUi(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String cid = req.getParameter("cid");
		Category category = categoryService.findCategoryById(cid);
		req.setAttribute("category", category);
		
		return "/admin/category/edit.jsp";
	}
	
	/**
	 * 编辑分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String editCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String cid = req.getParameter("cid");
		String name = req.getParameter("name");
		
		Category category = categoryService.findCategoryById(cid);
		category.setCname(name);
		categoryService.updateCategory(category);
		resp.sendRedirect("/Store/AdminCategoryServlet?method=findAllCats");
		return null;
	}
	
	/**
	 * 跳转到添加类别页面
	 */
	public String addUi(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return "/admin/category/add.jsp";
	}
	
	/**
	 * 添加分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String name = req.getParameter("name");
		
		Category category = new Category();
		category.setCname(name);
		category.setCid(UUIDUtils.getCode());
		categoryService.addCategory(category);
		resp.sendRedirect("/Store/AdminCategoryServlet?method=findAllCats");
		return null;
	}
	
	/**
	 * 删除类别
	 */
	public String delCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String cid = req.getParameter("cid");
		
		Category category = categoryService.findCategoryById(cid);
		categoryService.delCategory(category);
		resp.sendRedirect("/Store/AdminCategoryServlet?method=findAllCats");
		return null;
	}
}
