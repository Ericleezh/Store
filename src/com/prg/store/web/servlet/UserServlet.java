package com.prg.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prg.store.domain.User;
import com.prg.store.service.UserService;
import com.prg.store.service.impl.UserServiceImpl;
import com.prg.store.utils.MailUtils;
import com.prg.store.utils.MyBeanUtils;
import com.prg.store.utils.UUIDUtils;
import com.prg.store.web.base.BaseServlet;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	/*
	 * 主页跳转注册页面
	 */
	public String registUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

	/*
	 * 用户注册
	 */
	public String userRegist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//接受表单参数
		Map<String, String[]> map = req.getParameterMap();
		User user = new User();
		MyBeanUtils.populate(user, map);
		//为用户的其他属性赋值
		user.setCode(UUIDUtils.getCode());
		user.setUid(UUIDUtils.getId());
		user.setState(0);
		
		//调用业务层注册功能
		UserService userService = new UserServiceImpl();
		try {
			userService.userRegist(user);
			//注册成功，向用户发送邮件，跳转提示页面
			//发送邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
			req.setAttribute("msg", "用户注册成功，请到邮箱激活！");
		} catch (Exception e) {
			//注册失败，跳转提示页面
			req.setAttribute("msg", "用户注册失败，请重新注册！");
		}
		
		return "/jsp/info.jsp";
	}
	
	/**
	 * 激活用户
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public void active(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {

		String active_code = req.getParameter("code");
		
		UserService userService = new UserServiceImpl();
		Boolean is_success = userService.userActive(active_code);
		
		if (is_success) {
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Content-type", "text/html;charset=UTF-8");
			resp.setHeader("refresh", "3;url='jsp/login.jsp'");
			resp.getWriter().write("<cneter>激活成功，3秒后自动跳转至登录页面,若没有响应请点击<a href='http://localhost:8080/Store/jsp/login.jsp'>这里</a></center>");
		} else {
			resp.getWriter().write("激活失败！");
		}
	}

	/**
	 * 主页跳转登录页面
	 */
	public String loginUI(HttpServletRequest req, HttpServletResponse resp) {
		return "jsp/login.jsp";
	}
	
	/**
	 * 用户登录
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String userLogin(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
		
		Map<String, String[]> map = req.getParameterMap();
		User user = new User();
		MyBeanUtils.populate(user, map);
		
		UserService userService = new UserServiceImpl();
		User user02 = userService.userLogin(user);
		if (user02 != null) {
			req.getSession().setAttribute("user", user02);
			resp.sendRedirect("jsp/index.jsp");
			return null;
		} else {
			req.setAttribute("msg", "用户名或密码错误");
		}
		return "jsp/login.jsp";
	}
}
