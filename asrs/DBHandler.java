package asrs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class om met de database te verbinden
 * 
 * @author timpotze
 * 
 */
public class DBHandler {
	// User details:
	// 209.105.248.9
	// asrs
	// w1nd3sh31m

	public Connection connect() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			String url = "jdbc:mysql://209.105.248.9/asrs";
			return conn = DriverManager.getConnection(url, "asrs", "w1nd3sh31m");
		} catch (InstantiationException e) {
			//TODO: Handle error
		} catch (IllegalAccessException e) {
			//TODO: Handle error
		} catch (ClassNotFoundException e) {
			//TODO: Handle error
		} catch (SQLException e) {
			//TODO: Handle error
		} finally {
			return conn;
		}
	}
}
