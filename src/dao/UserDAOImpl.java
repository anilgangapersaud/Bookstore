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

import com.ibm.db2.jcc.am.ResultSet;

import domain.Login;
import domain.User;

@Repository
public class UserDAOImpl extends BaseDAO implements UserDAO {

	@Autowired
	DataSource datasource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void register(User user) throws Exception {
		String sql = "INSERT INTO USERS(username, password, firstname, lastname, email, role)  VALUES(:username, :password, :firstname, :lastname, :email, :role)";
		Map m = new HashMap();
		m.put("username", user.getUsername());
		m.put("password", user.getPassword());
		m.put("firstname", user.getFirstname());
		m.put("lastname", user.getLastname());
		m.put("email", user.getEmail());
		m.put("role", user.getRole());
		KeyHolder kh = new GeneratedKeyHolder();
		SqlParameterSource ps = new MapSqlParameterSource(m);
		super.getNamedParameterJdbcTemplate().update(sql, ps, kh);
		Integer userId= kh.getKey().intValue();
		user.setUserId(userId);
	}
	
	public User validateUser(Login login) {
		String sql = "SELECT * FROM USERS WHERE USERNAME='" + login.getUsername() + "' AND PASSWORD='" + login.getPassword() +"'";
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		return users.size() > 0 ? users.get(0) : null;
	}
}

class UserMapper implements RowMapper<User> {
	
	@Override
	public User mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
	User user = new User();
		
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setFirstname(rs.getString("firstname"));
		user.setLastname(rs.getString("lastname"));
		user.setEmail(rs.getString("email"));
		user.setRole(rs.getString("role"));
		user.setUserId(rs.getInt("userid"));
		return user;
	}
	
	
	
}