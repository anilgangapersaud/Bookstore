package dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import domain.Review;

public class ReviewDAOImpl extends BaseDAO implements ReviewDAO  {

	@Override
	public void save(Review r) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO REVIEWS(bid, rating, reviewer, review) VALUES (:bid, :rating, :reviewer, :review)";
		Map m = new HashMap();
		m.put("bid", r.getBid());
		m.put("rating", r.getRating());
		m.put("reviewer", r.getReviewer());
		m.put("review", r.getReview());
		SqlParameterSource ps = new MapSqlParameterSource(m);
		super.getNamedParameterJdbcTemplate().update(sql, ps);
		
	}


	@Override
	public List<Review> findAllReviews(String bid) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM REVIEWS where bid = '"+bid+"'";
		List<Review> reviews = getJdbcTemplate().query(sql, new ReviewMapper());
		return reviews;
	}
}
	class ReviewMapper implements RowMapper<Review> {
		
		@Override
		public Review mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
		Review b = new Review();
		
			b.setBid(rs.getString("BID"));
			b.setRating(Float.parseFloat(rs.getString("RATING")));
			b.setReview(rs.getString("REVIEW"));
			b.setReviewer(rs.getString("REVIEWER"));
			
			return b;
		}
	}


