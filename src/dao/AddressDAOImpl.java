package dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import domain.Address;
import domain.Book;

@Repository
public class AddressDAOImpl extends BaseDAO implements AddressDAO  {

	@Override
	public void save(Address a) {
		String sql = "INSERT INTO ADDRESS(userId, street, phone, zip, country, province) VALUES(:userId, :street, :phone, :zip, :country, :province)";
		Map m = new HashMap();
		m.put("userId", a.getUserid());
		m.put("street", a.getStreet());
		m.put("phone", a.getPhone());
		m.put("zip", a.getPostalCode());
		m.put("country", a.getCountry());
		m.put("province", a.getProvince());
		SqlParameterSource ps = new MapSqlParameterSource(m);
		super.getNamedParameterJdbcTemplate().update(sql, ps);
	}

	@Override
	public void update(Address a) {
		String sql = "UPDATE ADDRESS SET userId=:userId, street=:street, phone=:phone, zip=:zip, country=:country, province=:province WHERE addressid=:addressid";
		Map m = new HashMap();
		m.put("userId", a.getUserid());
		m.put("street", a.getStreet());
		m.put("phone", a.getPhone());
		m.put("zip", a.getPostalCode());
		m.put("country", a.getCountry());
		m.put("province", a.getProvince());
		m.put("addressid", a.getAddressid());
		SqlParameterSource ps = new MapSqlParameterSource(m);	
		super.getNamedParameterJdbcTemplate().update(sql, ps);
	}

	@Override
	public void delete(int addressid) {
		String sql = "DELETE FROM ADDRESS WHERE addressid=?";
		getJdbcTemplate().update(sql, addressid);
	}

	@Override
	public Address findById(int id) {
		String sql = "SELECT * FROM ADDRESS WHERE addressid=?";
		Address a = getJdbcTemplate().queryForObject(sql, new AddressMapper(), id);
		return a;
	}

	@Override
	public List<Address> findAll() {
		String sql = "SELECT * FROM ADDRESS";
		List<Address> a = getJdbcTemplate().query(sql, new AddressMapper());
		return a;
	}

	@Override
	public List<Address> findByProperty(String propName, Object propValue) {
		String sql = " SELECT * FROM ADDRESS WHERE "+propName+"=?";
		List<Address> a = getJdbcTemplate().query(sql, new AddressMapper(), propValue);
		return a;
	}

}

class AddressMapper implements RowMapper<Address> {
	
	@Override
	public Address mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
	Address a = new Address();
	
		a.setAddressid(rs.getInt("ADDRESSID"));
		a.setCountry(rs.getString("COUNTRY"));
		a.setPhone(rs.getString("PHONE"));
		a.setPostalCode(rs.getString("ZIP"));
		a.setProvince(rs.getString("PROVINCE"));
		a.setStreet(rs.getString("STREET"));
		a.setUserid(rs.getInt("USERID"));
		
		return a;
	}
	
	
	
}
