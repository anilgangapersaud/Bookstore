package dao;

import java.sql.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class TestBookDAO {

	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:db2://dashdb-txn-sbox-yp-dal09-03.services.dal.bluemix.net:50000/BLUDB:user=jss12325;password=k0m1022nx^dpz80r");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK");
		while (rs.next()) {
			String em = rs.getString("BID");
			System.out.println(em);
		}
		con.close();
	}
}
