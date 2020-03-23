package dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.ibm.db2.jcc.am.ResultSet;

import model.Login;
import model.User;

public class UserDAOImpl implements UserDAO {

	@Autowired
	DataSource datasource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void register(User user) throws Exception {
		String sql = "INSERT INTO USERS VALUES(?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, new Object[] { user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getAddress(), user.getPhone()});
		String sql2 = "INSERT INTO ADDRESS(street, zip, country, phone, province, username) VALUES(?,?,?,?,?,?)";
		jdbcTemplate.update(sql2, new Object[] {user.getAddress(), user.getPostalCode(), user.getCountry(), user.getPhone(), user.getProvince(), user.getUsername()});
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
		user.setAddress(rs.getString("address"));
		user.setPhone(rs.getString("phone"));
		
		return user;
	}
	
	
	
}