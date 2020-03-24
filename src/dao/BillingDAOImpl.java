package dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import domain.Address;
import domain.Billing;

public class BillingDAOImpl extends BaseDAO implements BillingDAO {

	@Override
	public void save(Billing b) {
		String sql = "INSERT INTO BILLING(cardtype, expdate, cardnumber, cardholder, userid) VALUES (:cardtype, :expdate, :cardnumber, :cardholder, :userid)";
		Map m = new HashMap();
		m.put("cardtype", b.getCardType());
		m.put("expdate", b.getExpDate());
		m.put("cardnumber", b.getCardNumber());
		m.put("cardholder", b.getCardholderName());
		m.put("userid", b.getUserid());
		SqlParameterSource ps = new MapSqlParameterSource(m);
		super.getNamedParameterJdbcTemplate().update(sql, ps);
	}

	@Override
	public void update(Billing b) {
		String sql = "UPDATE BILLING SET cardtype=:cardtype, expdate=:expdate, cardnumber=:cardnumber, cardholder=:cardholder, userid:userid WHERE cardid=:cardid";
		Map m = new HashMap();
		m.put("cardtype", b.getCardType());
		m.put("expdate", b.getExpDate());
		m.put("cardnumber", b.getCardNumber());
		m.put("cardholder", b.getCardholderName());
		m.put("userid", b.getUserid());
		m.put("cardid", b.getCardid());
		SqlParameterSource ps = new MapSqlParameterSource(m);	
		super.getNamedParameterJdbcTemplate().update(sql, ps);
	}

	@Override
	public void delete(Integer cardid) {
		String sql = "DELETE FROM BILLING WHERE cardid=?";
		getJdbcTemplate().update(sql, cardid);
	}

	@Override
	public Billing findById(String id) {
		String sql = "SELECT * FROM BILLING WHERE cardid=?";
		Billing b = getJdbcTemplate().queryForObject(sql, new BillingMapper(), id);
		return b;
	}

	@Override
	public List<Billing> findAll() {
		String sql = "SELECT * FROM ADDRESS";
		List<Billing> b = getJdbcTemplate().query(sql, new BillingMapper());
		return b;
	}

	@Override
	public List<Billing> findByProperty(String propName, Object propValue) {
		String sql = " SELECT * FROM BILLING WHERE "+propName+"=?";
		List<Billing> a = getJdbcTemplate().query(sql, new BillingMapper(), propValue);
		return a;
	}

}

class BillingMapper implements RowMapper<Billing> {
	
	@Override
	public Billing mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
	Billing b = new Billing();
	
		b.setCardholderName(rs.getString("CARDHOLDER"));
		b.setCardid(rs.getInt("CARDID"));
		b.setCardNumber(rs.getString("CARDNUMBER"));
		b.setCardType(rs.getString("CARDTYPE"));
		b.setExpDate(rs.getString("EXPDATE"));
		b.setUserid(rs.getInt("USERID"));
			
		
		return b;
	}
	
	
	
}
