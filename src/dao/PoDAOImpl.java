package dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import domain.Book;
import domain.PO;

@Repository
public class PoDAOImpl extends BaseDAO implements PoDAO {

	
	@Autowired
	DataSource datasource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public void save(PO p) {
		String sql = "INSERT INTO PO(status, orderDate, addressid, cardid) VALUES (:status, :orderDate, :addressid, :cardid)";
		Map m = new HashMap();
		m.put("status", p.getStatus());
		m.put("orderDate", p.getOrderDate());
		m.put("addressid", p.getAddressID());
		m.put("cardid", p.getCardID());
		KeyHolder kh = new GeneratedKeyHolder();
		SqlParameterSource ps = new MapSqlParameterSource(m);
		super.getNamedParameterJdbcTemplate().update(sql, ps, kh);
		Integer poid = kh.getKey().intValue();
		p.setPurchaseID(poid);
	}

	@Override
	public void update(PO p) {
		String sql = "UPDATE PO SET orderDate=:orderDate, addressid=:addressid, cardid=:cardid status=:status, WHERE id=:id";
		Map m = new HashMap();
		m.put("id", p.getPurchaseID());
		m.put("status", p.getStatus());
		m.put("orderDate", p.getOrderDate());
		m.put("addressid", p.getAddressID());
		m.put("cardid", p.getCardID());
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
	
		p.setPurchaseID(rs.getInt("ID"));
		p.setStatus(rs.getString("STATUS"));
		p.setAddressID(rs.getInt("ADDRESSID"));
		p.setCardID(rs.getInt("CARDID"));
		p.setOrderDate(rs.getString("ORDERDATE"));
		
		return p;
	}
}
