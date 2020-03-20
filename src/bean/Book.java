package bean;

public class Book {

	private String bid;
	private String title;
	private double price;
	private String category;
	private String author;
	public Book(String bid, String title, double price, String category, String author) {
		this.bid = bid;
		this.title = title;
		this.price = price;
		this.category = category;
		this.author = author;
	}
	public String getBid() {
		return bid;
	}
	public String getAuthor() {
		return author;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public void setAuthor(String author) {
		this.author = author;
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
