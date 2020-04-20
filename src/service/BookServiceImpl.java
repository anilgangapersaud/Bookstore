package service;

import java.sql.SQLDataException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.BookDAO;
import dao.ReviewDAO;
import domain.Book;
import domain.Review;
import dao.BookDAO;

@Service
public class BookServiceImpl implements BookService {

	@Autowired 
	public BookDAO bookdao;
	
	@Autowired 
	public ReviewDAO reviewdao;
	
	@Override
	public void create(Book b) {
		 bookdao.save(b);
	}

	@Override
	public void update(Book b) {
		bookdao.update(b);
	}

	@Override
	public void delete(String bid) {
		bookdao.delete(bid);
	}

	@Override
	public Book findById(String bid) {
		return bookdao.findById(bid);
	}

	@Override
	public List<Book> findAll() {
		return bookdao.findAll();
	}

	@Override
	public List<Book> findByProperty(String propName, Object propValue) {
		return bookdao.findByProperty(propName, propValue);
	}
	@Override
	public List<Review> findAllReviews(String bid) {
		return reviewdao.findAllReviews(bid);
	}
	@Override
	public void save(Review a)
	{
		reviewdao.save(a);
	}
	
	
	public float findRatingAverage(List<Review> r)
	{
		float totalAverage = 0;
		for(Review reviews: r)
		{
			totalAverage+= reviews.getRating();
		}
		if(r.size() != 0)
			return (totalAverage/r.size());
		else
			return 0;
	}
	public int increaseQuantity(int quantity)
	{
		return ++quantity;
	}

	@Override
	public List<Book> searchByTitle(String title) {
		return bookdao.searchByTitle(title);
	}

	@Override
	public List<Book> searchByAuthor(String author) {
		return bookdao.searchByAuthor(author);
	}
	@Override
	public List<Book> searchTopBooks()
	{
		return bookdao.searchTopBooks();
	}
	

}
