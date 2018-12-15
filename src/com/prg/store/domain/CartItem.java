package com.prg.store.domain;

public class CartItem {
	private Product product;
	private int num; //购物项中的数量
	private double subTotal; //小计,num*product.shop_price
	
	public double getSubTotal() {
		return num*product.getShop_price();
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
}
