package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import domain.Book;
import domain.User;

@Repository
public class BookDAOImpl extends BaseDAO implements BookDAO{

	@Override
	public void save(Book b) {
		String sql = "INSERT INTO BOOK(bid, title, price, category) VALUES (:bid, :title, :price, :category, :author)";
		Map m = new HashMap();
		m.put("bid", b.getBid());
		m.put("title", b.getTitle());
		m.put("price", b.getPrice());
		m.put("category", b.getCategory());
		m.put("author", b.getAuthor());
		SqlParameterSource ps = new MapSqlParameterSource(m);
		super.getNamedParameterJdbcTemplate().update(sql, ps);
	}

	@Override
	public void update(Book b) {
		String sql = "UPDATE BOOK SET title=:title, price=:price, category=:category, author=:author WHERE bid=:bid";
		Map m = new HashMap();
		m.put("title", b.getTitle());
		m.put("price", b.getPrice());
		m.put("category", b.getCategory());
		m.put("author", b.getAuthor());
		m.put("bid", b.getBid());
		SqlParameterSource ps = new MapSqlParameterSource(m);	
		super.getNamedParameterJdbcTemplate().update(sql, ps);
	}

	@Override
	public void delete(String bid) {
		String sql = "DELETE FROM BOOK WHERE bid=?";
		getJdbcTemplate().update(sql, bid);
	}

	@Override
	public Book findById(String bid) {
		String sql = "SELECT * FROM BOOK WHERE bid=?";
		Book b = getJdbcTemplate().queryForObject(sql, new BookMapper(), bid);
		return b;
	}

	@Override
	public List<Book> findAll() {
		String sql = "SELECT * FROM BOOK";
		List<Book> books = getJdbcTemplate().query(sql, new BookMapper());
		return books;
	}
	
	

	@Override
	public List<Book> findByProperty(String propName, Object propValue) {
		String sql = " SELECT * FROM BOOK WHERE "+propName+"=?";
		List<Book> books = getJdbcTemplate().query(sql, new BookMapper(), propValue);
		return books;
	}
	
	

}

class BookMapper implements RowMapper<Book> {
	
	@Override
	public Book mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
	Book b = new Book();
	
		b.setBid(rs.getString("BID"));
		b.setTitle(rs.getString("TITLE"));
		b.setPrice(Double.parseDouble(rs.getString("PRICE")));
		b.setCategory(rs.getString("CATEGORY"));
		b.setAuthor(rs.getString("AUTHOR"));
		
		return b;
	}
	
	
	
}
