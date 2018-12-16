package com.prg.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prg.store.domain.User;

/**
 * Servlet Filter implementation class Filter03_Privilege
 */
@WebFilter(urlPatterns = {"/jsp/order_info.jsp","/jsp/order_list.jsp","/jsp/cart.jsp"})
public class Filter03_Privilege implements Filter {

    public Filter03_Privilege() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		User user = (User) req.getSession().getAttribute("user");
		//用戶未登錄
		if (user == null) {
			req.setAttribute("msg", "請先登錄后再訪問！");
			req.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
			chain.doFilter(req, response);
		} else {
			chain.doFilter(request, response);
		}
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
