package itcast.domain;

public class OrderItem {

	private Order order;
	private Product product;
	private int buyNum;
	private double itemMoney;
	
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public double getItemMoney() {
		return itemMoney;
	}
	public void setItemMoney(double itemMoney) {
		this.itemMoney = itemMoney;
	}
	
	
	
}
