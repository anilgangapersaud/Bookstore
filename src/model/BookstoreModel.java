package model;

import java.sql.SQLException;
import java.util.Map;

import bean.Book;
import dao.BookDAO;

public class BookstoreModel {
	private BookDAO books;
	
	public BookstoreModel() {
		try {
			books = new BookDAO();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Book> retrieveBook(String bid) throws SQLException {
		return books.retrieve(bid);
	}
	
	public Map<String, Book> retrieveAllBooks() throws SQLException {
		return books.retrieveAllBooks();
	}
}
