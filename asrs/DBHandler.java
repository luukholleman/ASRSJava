package asrs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

	public static Connection connect() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			String url = "jdbc:mysql://209.105.248.9/asrs";
			return conn = DriverManager
					.getConnection(url, "asrs", "w1nd3sh31m");
		} catch (InstantiationException e) {
			System.out.println("Initialisation error");
			// TODO: Handle error
		} catch (IllegalAccessException e) {
			System.out.println("IlligalAccess error");
			// TODO: Handle error
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFound error");
			// TODO: Handle error
		} catch (SQLException e) {
			System.out.println("SQL error");
			// TODO: Handle error
		}
		return conn;
	}
	
	public static void test()
	{
		Connection conn = connect();
		
		System.out.println("[OUTPUT FROM SELECT]");
	    String query = "SELECT * FROM products";
	    try
	    {
	      Statement st = conn.createStatement();
	      ResultSet rs = st.executeQuery(query);
	      while (rs.next())
	      {
	    	  String x = rs.getString("x");
	    	  String y = rs.getString("y");
		        
	        System.out.println(x + "   " + y);
	      }
	    }
	    catch (SQLException ex)
	    {
	      System.err.println(ex.getMessage());
	    }
	}
}
