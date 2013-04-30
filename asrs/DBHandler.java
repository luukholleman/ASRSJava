package asrs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import order.Location;
import order.Product;

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

	private Connection connect() {
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
	
	public ArrayList<Location> getAllOccupiedLocations()
	{
		String query = "SELECT * FROM products";
		ArrayList<Location> locs = new ArrayList<Location>();
		
	    try
	    {
	    	Connection conn = connect();
	    	Statement st = conn.createStatement();
	    	ResultSet rs = st.executeQuery(query);
	    	while (rs.next())
	    	{
	    		int x = rs.getInt("x");
	    		int y = rs.getInt("y");
	    		locs.add(new Location(x,y));
	    	}
	    	
	    	conn.close();
	    }
	    catch (SQLException ex)
	    {
	    	ex.printStackTrace();
	    }
	    return locs;
	}
	
	public void getProductDatabaseInfo(Product product)
	{
		String query = "SELECT * FROM products WHERE artnr=" + product.getId() + " ORDER BY y ASC LIMIT 1";
	    try
	    {
	    	Connection conn = connect();
	    	Statement st = conn.createStatement();
	    	ResultSet rs = st.executeQuery(query);
	    	boolean found = rs.next();
	    	
	    	if(found){
	    		int x = rs.getInt("x");
	    		int y = rs.getInt("y");
	    		int size = rs.getInt("size");
	    		
	    		product.setLocation(new Location(x,y));
	    		product.setSize(size);
	    	}
	    	
	    	conn.close();
	    }
	    catch (SQLException ex)
	    {
	    	ex.printStackTrace();
	    }
	}
}
