package com.prg.store.web.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取前端method参数
		String md = request.getParameter("method");
		//防止找不到方法的情况
		if (null == md || "".equals(md) || md.trim().equals("")) {
			md = "execute";
		}
		String path = null;
		//获取到具体的继承类
		Class clazz = this.getClass();
		try {
			Method method = clazz.getMethod(md, HttpServletRequest.class, HttpServletResponse.class);
			if (method != null) {
				//调用具体找到的方法，并返回方法的转发地址
				path = (String)method.invoke(this, request, response);
			}
			//在这里统一做转发
			if (path != null) {
				request.getRequestDispatcher(path).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		return null;
	}
}
