package asrs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

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
			JOptionPane.showMessageDialog(null, "Kan niet met de database verbinden.\n\nStackTrace:\n" + e.getStackTrace());
		} catch (IllegalAccessException e) {
			JOptionPane.showMessageDialog(null, "Kan niet met de database verbinden.\n\nStackTrace:\n" + e.getStackTrace());
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Kan niet met de database verbinden.\n\nStackTrace:\n" + e.getStackTrace());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kan niet met de database verbinden.\n\nStackTrace:\n" + e.getStackTrace());
		}
		return conn;
	}
}
