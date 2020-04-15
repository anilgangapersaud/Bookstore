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

import domain.PO;
import domain.POItem;

public class POItemDAOImpl extends BaseDAO implements POItemDAO {

	@Autowired
	DataSource datasource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public void save(POItem p) {
		String sql = "INSERT INTO POItem(id, bid, price, quantity) VALUES (:id, :bid, :price, :quantity)";
		Map m = new HashMap();
		m.put("id", p.getItemId());
		m.put("bid", p.getBid());
		m.put("price", p.getPrice());
		m.put("quantity", p.getQuantity());
		SqlParameterSource ps = new MapSqlParameterSource(m);
		super.getNamedParameterJdbcTemplate().update(sql, ps);
	}

	@Override
	public void update(POItem p) {
		String sql = "UPDATE POItem SET bid=:bid, price=:price, quantity=:quantity WHERE id=:id";
		Map m = new HashMap();
		m.put("bid", p.getBid());
		m.put("price", p.getPrice());
		m.put("quantity", p.getQuantity());
		m.put("id", p.getItemId());
		SqlParameterSource ps = new MapSqlParameterSource(m);	
		super.getNamedParameterJdbcTemplate().update(sql, ps);
	}

	@Override
	public void delete(Integer itemid) {
		String sql = "DELETE FROM POItem WHERE id=?";
		getJdbcTemplate().update(sql, itemid);
	}

	@Override
	public POItem findById(Integer itemid) {
		String sql = "SELECT * FROM POItem WHERE id=?";
		POItem p = getJdbcTemplate().queryForObject(sql, new POItemMapper(), itemid);
		return p;
	}

	@Override
	public List<POItem> findAll() {
		String sql = "SELECT * FROM POItem";
		List<POItem> POItems = getJdbcTemplate().query(sql, new POItemMapper());
		return POItems;
	}

	@Override
	public List<POItem> findByProperty(String propName, Object propValue) {
		String sql = "SELECT * FROM POItem WHERE "+propName+"=?";
		List<POItem> POItems = getJdbcTemplate().query(sql, new POItemMapper(), propValue);
		return POItems;
	}
	
	@Override
	public List<POItem> findByOrderDate(String month, String year){
		String sql="SELECT * FROM POItem, PO WHERE POItem.id = PO.id and PO.orderdate like '"+year+"-"+month+"%'ORDER BY PO.orderdate, POItem.bid ";
		List<POItem> POItems = getJdbcTemplate().query(sql, new POItemMapper());
		return POItems;
	}

}
class POItemMapper implements RowMapper<POItem> {
	
	@Override
	public POItem mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
	POItem p = new POItem();
	
		p.setItemId(rs.getInt("ID"));
		p.setBid(rs.getString("BID"));
		p.setPrice(Double.parseDouble(rs.getString("PRICE")));
		p.setQuantity(rs.getInt("QUANTITY"));
		
		return p;
	}
}
