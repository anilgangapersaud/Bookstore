package service;

import java.util.List;

import domain.Book;
import domain.Review;

public interface BookService {
	public void create(Book b);
	public void update(Book b);
	public void delete(String bid);
	public Book findById(String bid);
	public List<Book> findAll();
	public List<Book> findByProperty(String propName, Object propValue);
	public void save(Review a);
	public List<Review> findAllReviews(String bid);
	public float findRatingAverage(List<Review> r);
	public int increaseQuantity(int quantity);
	public List<Book> searchByTitle(String title);
	public List<Book> searchByAuthor(String author);
}
