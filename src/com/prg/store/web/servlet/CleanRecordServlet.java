package com.prg.store.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prg.store.web.base.BaseServlet;

@WebServlet("/CleanRecordServlet")
public class CleanRecordServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public String cleanHistory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//清除cookie
		Cookie cookie = new Cookie("history", null);
		cookie.setMaxAge(0);
		resp.addCookie(cookie);
		
		//TODO:待改进
		resp.sendRedirect("jsp/product_list.jsp");
		return null;
	}
}
