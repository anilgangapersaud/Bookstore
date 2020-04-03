package dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import domain.Book;
import domain.PO;

@Repository
public class PoDAOImpl extends BaseDAO implements PoDAO {

	@Override
	public void save(PO p) {
		String sql = "INSERT INTO PO(id, lname, fname, status, address) VALUES (:id, :lname, :fname, :status, :address)";
		Map m = new HashMap();
		m.put("id", p.getId());
		m.put("lname", p.getLastname());
		m.put("fname", p.getFirstname());
		m.put("status", p.getStatus());
		m.put("address", p.getAddressid());
		SqlParameterSource ps = new MapSqlParameterSource(m);
		super.getNamedParameterJdbcTemplate().update(sql, ps);
	}

	@Override
	public void update(PO p) {
		String sql = "UPDATE PO SET lname=:lname, fname=:fname, status=:status, address=:address WHERE id=:id";
		Map m = new HashMap();
		m.put("lname", p.getLastname());
		m.put("fname", p.getFirstname());
		m.put("status", p.getStatus());
		m.put("address", p.getAddressid());
		m.put("id", p.getId());
		SqlParameterSource ps = new MapSqlParameterSource(m);	
		super.getNamedParameterJdbcTemplate().update(sql, ps);
	}

	@Override
	public void delete(Integer poid) {
		String sql = "DELETE FROM PO WHERE id=?";
		getJdbcTemplate().update(sql, poid);
	}

	@Override
	public PO findById(Integer poid) {
		String sql = "SELECT * FROM PO WHERE id=?";
		PO p = getJdbcTemplate().queryForObject(sql, new POMapper(), poid);
		return p;
	}

	@Override
	public List<PO> findAll() {
		String sql = "SELECT * FROM PO";
		List<PO> PO = getJdbcTemplate().query(sql, new POMapper());
		return PO;
	}

	@Override
	public List<PO> findByProperty(String propName, Object propValue) {
		String sql = " SELECT * FROM PO WHERE "+propName+"=?";
		List<PO> PO = getJdbcTemplate().query(sql, new POMapper(), propValue);
		return PO;
	}

	@Override
	public List<PO> getOrdersByBid(String bid) {
		String sql = "SELECT * FROM PO p JOIN POITEM i ON p.id = i.id WHERE i.bid = ?";
		List<PO> PO = getJdbcTemplate().query(sql, new POMapper(), bid);
		return PO;
	} 
}

class POMapper implements RowMapper<PO> {
	
	@Override
	public PO mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
	PO p = new PO();
	
		p.setId(rs.getInt("ID"));
		p.setFirstname(rs.getString("FNAME"));
		p.setLastname(rs.getString("LNAME"));
		p.setAddressid(rs.getInt("ADDRESS"));
		p.setStatus(rs.getString("STATUS"));
		
		return p;
	}
}
