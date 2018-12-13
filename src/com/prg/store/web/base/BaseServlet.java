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
		String method = request.getParameter("method");
		//防止找不到方法的情况
		if (null == method || "".equals(method) || method.trim().equals("")) {
			method = "execute";
		}
		//获取到具体的继承类
		Class clazz = this.getClass();
		try {
			Method md = clazz.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
			if (md != null) {
				//调用具体找到的方法，并返回方法的转发地址
				String path = (String)md.invoke(this, request, response);
				//在这里统一做转发
				if (path != null) {
					request.getRequestDispatcher(path).forward(request, response);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return null;
	}
}
