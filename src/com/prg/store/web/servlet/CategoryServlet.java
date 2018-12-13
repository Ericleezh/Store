package com.prg.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prg.store.domain.Category;
import com.prg.store.service.CategoryService;
import com.prg.store.service.impl.CategoryServiceImpl;
import com.prg.store.utils.JedisUtils;
import com.prg.store.web.base.BaseServlet;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		//先在缓存中取数据，如果有则先拿缓存中的
		Jedis jedis = JedisUtils.getJedis();
		String json_cats = jedis.get("all_cats");
		
		//没有再去数据库查询
		if (json_cats == null || "".equals(json_cats)) {
			CategoryService categoryService = new CategoryServiceImpl();
			List<Category> list = categoryService.findAllCats();
			json_cats = JSONArray.fromObject(list).toString();
			jedis.set("all_cats", json_cats);
		}
		resp.setContentType("application/json;charset=utf-8");
		resp.getWriter().write(json_cats);
		
		//关闭资源
		JedisUtils.closeJedis(jedis);
		return null;
	}
}
