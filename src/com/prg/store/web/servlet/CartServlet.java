package com.prg.store.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prg.store.domain.Cart;
import com.prg.store.domain.CartItem;
import com.prg.store.domain.Product;
import com.prg.store.service.ProductService;
import com.prg.store.service.impl.ProductServiceImpl;
import com.prg.store.web.base.BaseServlet;
import com.sun.org.apache.bcel.internal.generic.NEW;


/**
 * TODO:相关方法在逻辑上还有一些问题
 * @author PearRealGood
 *
 */
@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public String addCartItemsToCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//从session中获取购物车
		Cart cart = (Cart)req.getSession().getAttribute("cart");
		//还没有购物车
		if (cart == null) {
			cart = new Cart();
			req.getSession().setAttribute("cart", cart);
		}
		
		//获取前端传过来的商品id，购买数量
		String pid = req.getParameter("pid");
		int num = Integer.parseInt(req.getParameter("quantity"));
		
		//查询具体的商品
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProductById(pid);
		
		//获取购物项
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setNum(num);

		//将购物项存储到购物车中
		cart.addCartItemToCart(cartItem);
		resp.sendRedirect("jsp/cart.jsp");
		return null;
	}

	public String removeItemFromCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String pid = req.getParameter("pid");
		
		//从session中获取购物车
		Cart cart = (Cart)req.getSession().getAttribute("cart");
		
		cart.removeCartItem(pid);
		System.out.println("66666666666666");
		resp.sendRedirect("jsp/cart.jsp");
		return null;
	}
	
	public String ClearCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		cart.clearCartItem();
		resp.sendRedirect("jsp/cart.jsp");
		return null;
	}
}
