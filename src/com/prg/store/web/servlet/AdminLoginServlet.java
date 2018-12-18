package com.prg.store.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prg.store.domain.User;
import com.prg.store.service.UserService;
import com.prg.store.service.impl.UserServiceImpl;
import com.prg.store.web.base.BaseServlet;

/**
 * Servlet implementation class AdminLoginServlet
 */
@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;
	
	UserService userService = new UserServiceImpl();
	
	public String login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User user = userService.login(username, password);
		req.getSession().setAttribute("user", user);
		if (user == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			return "/admin/index.jsp";
		}
		
		return "/admin/home.jsp";
	}
}
