package dao;

import java.sql.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class TestBookDAO {
	public static void main(String[] args) throws SQLException {
	try {
		DataSource ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/springDataSource");
		Connection con = ds.getConnection();
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK");
		while(rs.next()) {
			System.out.println(rs.getString("BID"));
		}
		con.close();
} catch (NamingException e) {
	e.printStackTrace();
}
	} 
}
