package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import bean.Book;

public class BookDAO {
	
	private DataSource ds;
	
	public BookDAO() throws ClassNotFoundException {
		try {
			 ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/springDataSource");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Book> retrieve(String bid) throws SQLException {
		String query = "SELECT * FROM BOOK WHERE BID = '" + bid + "'" ;
		Map<String, Book> rv = new HashMap<String, Book>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String bookID = r.getString("BID");
			String title = r.getString("TITLE");
			double price = Double.parseDouble(r.getString("PRICE"));
			String category = r.getString("CATEGORY");
			rv.put(bookID, new Book(bookID, title, price, category));
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
	
	public Map<String, Book> retrieveAllBooks() throws SQLException {
		String query = "SELECT * FROM BOOK";
		Map<String, Book> rv = new HashMap<String, Book>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while(r.next()) {
			String bookID = r.getString("BID");
			String title = r.getString("TITLE");
			double price = Double.parseDouble(r.getString("PRICE"));
			String category = r.getString("CATEGORY");
			rv.put(bookID, new Book(bookID, title, price, category));
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}
}
