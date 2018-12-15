package com.prg.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
	private double total; //总计
	
	Map<String, CartItem> map = new HashMap<String, CartItem>();
	
	
	/**
	 *添加购物项到购物车 
	 */
	public void addCartItemToCart(CartItem cartItem) {
		//判断购物车中是否有该购物项，有则直接加数量
		String pid = cartItem.getProduct().getPid();
		if (map.containsKey(pid)) {
			CartItem old_item = map.get(pid);
			old_item.setNum(old_item.getNum()+cartItem.getNum());
		} else {
			map.put(pid, cartItem);
		}
	}
	
	/**
	 * 获取map中的values
	 */
	public Collection getCartItems() {
		return map.values();
	}
	
	/**
	 * 从购物车移除购物项
	 */
	public void removeCartItem(String pid) {
		map.remove(pid);
	}
	
	/**
	 * 清空购物车
	 */
	public void clearCartItem() {
		map.clear();
	}
	
	public double getTotal() {
		total = 0;
		
		Collection<CartItem> cartItems = map.values();
		for (CartItem cartItem : cartItems) {
			total += cartItem.getSubTotal();
		}
		
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}

}
