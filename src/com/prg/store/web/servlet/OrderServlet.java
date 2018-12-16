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
import com.prg.store.domain.PageModel;
import com.prg.store.domain.User;
import com.prg.store.service.OrderService;
import com.prg.store.service.impl.OrderServiceImpl;
import com.prg.store.utils.PaymentUtil;
import com.prg.store.utils.UUIDUtils;
import com.prg.store.web.base.BaseServlet;

@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String AddOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//确认用户登录状态
		User user = (User)req.getSession().getAttribute("user");
		if (user == null) {
			req.setAttribute("msg", "user session invalid！");
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

	public String findMyOrdersWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取登录用户,当前页
		User user = (User)req.getSession().getAttribute("user");
		if (user == null) {
			resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().write("用戶 session 失效");
			return null;
		}
		int current_page = Integer.parseInt(req.getParameter("page"));
		
		//调用业务层获取pagemodel对象
		OrderService orderService = new OrderServiceImpl();
		PageModel pm = orderService.findMyOrdersWithPage(current_page, user);
		
		//将pm对象放到request中
		req.setAttribute("page", pm);
		
		return "/jsp/order_list.jsp";
	}

	public String findOrderByOId(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String order_id = req.getParameter("OId");
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOId(order_id);
		
		req.setAttribute("order", order);
		return "/jsp/order_info.jsp";
	}
	
	public String payOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//獲取信息
		String address = req.getParameter("address");
		String name = req.getParameter("name");
		String telephone = req.getParameter("telephone");
		String pd_FrpId = req.getParameter("pd_FrpId");
		String oid = req.getParameter("oid");
		
		//查詢訂單
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOId(oid);
		//更新訂單中收件人信息
		order.setAddress(address);
		order.setName(name);
		order.setTelephone(telephone);
		orderService.updateOrder(order);
		
		// 把付款所需要的参数准备好:
		String p0_Cmd = "Buy";
		//商户编号
		String p1_MerId = "10001126856";
		//订单编号
		String p2_Order = oid;
		//金额
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		//接受响应参数的Servlet
		String p8_Url = "http://localhost:8080/Store/OrderServlet?method=payCallBack";
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		//公司的秘钥
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
			
		//调用易宝的加密算法,对所有数据进行加密,返回电子签名
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
				
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);

		System.out.println(sb.toString());
		// 使用重定向：
		resp.sendRedirect(sb.toString());		
		
		return null;
	}
	
	/**
	 * 支付回調接口
	 */
	public String payCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 验证请求来源和数据有效性
		// 阅读支付结果参数说明
		// System.out.println("==============================================");
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");

		// hmac
		String hmac = request.getParameter("hmac");
		// 利用本地密钥和加密算法 加密数据
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				
				//支付成功，修改訂單狀態
				OrderService orderService = new OrderServiceImpl();
				Order order = orderService.findOrderByOId(r6_Order);
				order.setState(2);
				orderService.updateOrder(order);
				
				request.setAttribute("msg", "支付成功！订单号：" + r6_Order + "    金额：" + r3_Amt + "元");
				return "/jsp/info.jsp";
			} else if (r9_BType.equals("2")) {
				// 修改订单状态:
				// 服务器点对点，来自于易宝的通知
				System.out.println("收到易宝通知，修改订单状态！");//
				// 回复给易宝success，如果不回复，易宝会一直通知
				response.getWriter().print("success");
			}

		} else {
			throw new RuntimeException("数据被篡改！");
		}
		return null;
	}
}
