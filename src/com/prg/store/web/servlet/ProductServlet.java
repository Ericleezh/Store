package com.prg.store.web.servlet;

import com.prg.store.domain.PageModel;
import com.prg.store.domain.Product;
import com.prg.store.service.ProductService;
import com.prg.store.service.impl.ProductServiceImpl;
import com.prg.store.utils.CookUtils;
import com.prg.store.web.base.BaseServlet;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

@WebServlet("/ProductServlet")
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String productInfo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		String pid = req.getParameter("pid");
		
		Cookie[] cookies = req.getCookies();
		//获取历史记录cookie
		Cookie c = CookUtils.getCookieByName("history", cookies);
		
		//判断是否有浏览记录
		if (c == null) {
			Cookie cookie = new Cookie("history", pid);
			cookie.setMaxAge(7*24*60*60);
			resp.addCookie(cookie);
		} else {
			//有浏览记录
			String history_ids = c.getValue();
			String[] ids = history_ids.split("&");
			//判断该商品是否有浏览过，有则将其放到最前面
			if (Arrays.asList(ids).contains(pid) == true) {
				LinkedList<String> link_ids = new LinkedList<>(Arrays.asList(ids));
				//将最新浏览的id放到最前面
				link_ids.remove(pid);
				link_ids.addFirst(pid);
			    String new_ids = StringUtils.join(link_ids.toArray(new String[0]),"&");
			    c.setValue(new_ids);
			} else {
				//没有浏览过，直接添加
				c.setValue(pid+ "&" + history_ids);
			}
			resp.addCookie(c);
		}
		
		ProductService productService = new ProductServiceImpl();
		Product product= productService.findProductById(pid);
		
		req.setAttribute("product", product);
		
		return "/jsp/product_info.jsp";
	}
	
	public String findProductsWithCidAndPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		String cid = req.getParameter("cid");
		int current_page = Integer.parseInt(req.getParameter("page"));
		
		ProductService productService = new ProductServiceImpl();
		PageModel pageModel= productService.findProductsWithCidAndPage(cid, current_page);
		
		req.setAttribute("page", pageModel);
		
		//在这里获取浏览记录列表
		
		Cookie[] cookies = req.getCookies();
		//获取历史记录cookie
		Cookie c = CookUtils.getCookieByName("history", cookies);
		if (c == null) {
			req.setAttribute("msg", "<h3>您还没有浏览记录</h3>");
		} else {
			//有浏览记录
			String history_ids = c.getValue();
			//获取到所有的商品id
			String[] ids = history_ids.split("&");
			List<Product> list = productService.findHistoryProducts(ids);
			req.setAttribute("history_list", list);
		}
		
		return "/jsp/product_list.jsp";
	}
}
