package com.prg.store.web.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.prg.store.domain.User;
import com.prg.store.service.UserService;
import com.prg.store.service.impl.UserServiceImpl;
import com.prg.store.utils.CookUtils;

/**
 * Servlet Filter implementation class AutoLoginFilter
 */
@WebFilter("/Filter02_AutoLogin")
public class Filter02_AutoLogin implements Filter {
    public Filter02_AutoLogin() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		System.out.println("filter222 前面");
//		try {
//			HttpServletRequest request2 = (HttpServletRequest) request; 
//			User userBean = (User) request2.getSession().getAttribute("user");
//			//sessoin有效
//			if (userBean != null) {
//				chain.doFilter(request2, response);
//			} else {
//				//session失效
//				
//				//获取具体的cookie
//				Cookie[] cookies = request2.getCookies();	
//				Cookie cookie = CookUtils.getCookieByName("auto_login", cookies);
//				
//				//第一次登陆
//				if (cookie == null) {
//					chain.doFilter(request2, response);
//				} else {
//					//已经登陆过
//					String[] cvalue = cookie.getValue().split("#");
//					String username = cvalue[0];
//					URLDecoder.decode(username,"utf-8");
//					String password = cvalue[1];
//					
//					User user = new User();
//					user.setUsername(username);
//					user.setPassword(password);
//					
//					UserService userService = new UserServiceImpl();
//					userBean = userService.userLogin(user);
//					
//					request2.getSession().setAttribute("user", userBean);
//					chain.doFilter(request2, response);
//				}
//			}	
//		} catch (Exception e) {
//			e.printStackTrace();
//			chain.doFilter(request, response);
//		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
