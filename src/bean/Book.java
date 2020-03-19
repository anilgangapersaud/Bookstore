package bean;

public class Book {

	private String bid;
	private String title;
	private double price;
	private String category;
	public Book(String bid, String title, double price, String category) {
		this.bid = bid;
		this.title = title;
		this.price = price;
		this.category = category;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
}
