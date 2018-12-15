package com.prg.store.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prg.store.domain.Cart;
import com.prg.store.domain.CartItem;
import com.prg.store.domain.Order;
import com.prg.store.domain.OrderItem;
import com.prg.store.domain.User;
import com.prg.store.service.OrderService;
import com.prg.store.service.impl.OrderServiceImpl;
import com.prg.store.utils.UUIDUtils;
import com.prg.store.web.base.BaseServlet;

@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String AddOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//确认用户登录状态
		User user = (User)req.getSession().getAttribute("user");
		if (user == null) {
			req.setAttribute("msg", "请先登录！");
			return "/jsp/info.jsp";
		}
		//获取购物车
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		//创建订单对象，并为订单对象赋值
		Order order = new Order();
		order.setOid(UUIDUtils.getCode());
		order.setOrdertime(new Date());
		order.setUser(user);
		order.setTotal(cart.getTotal());
		order.setState(1);
		for (CartItem item : cart.getCartItems()) {
			//创建订单项
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getCode());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getNum());
			orderItem.setTotal(item.getSubTotal());
			orderItem.setOrder(order);
			order.getList().add(orderItem);
		}
		//业务层保存订单
		OrderService orderService = new OrderServiceImpl();
		orderService.addOrder(order);
		//清空购物车
		cart.clearCartItem();
		//将订单放入request域中，
		req.setAttribute("order", order);
		//转发页面
		return "/jsp/order_info.jsp";
	}
}
