package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import productInfo.Location;
import productInfo.Product;
import productInfo.ProductNotFoundException;

/**
 * Class om met de database te verbinden
 * 
 * @author Tim
 * 
 */
public class Database {

	/**
	 * Database connection
	 */
	private static Connection dbConnection;

	/**
	 * Maakt de connectie met de database
	 * 
	 * @throws DatabaseConnectionFailedException
	 */
	public static void connect() throws DatabaseConnectionFailedException {
		try {
			// Start de mysql driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// Maak database connectie aan
			dbConnection = DriverManager.getConnection(
					"jdbc:mysql://209.105.248.9/asrs", "asrs", "w1nd3sh31m");

		} catch (InstantiationException e) {
			throw new DatabaseConnectionFailedException(
					"Kan niet met de database verbinden(Initalization failed).");
		} catch (IllegalAccessException e) {
			throw new DatabaseConnectionFailedException(
					"Kan niet met de database verbinden(Illigal action performed).");
		} catch (ClassNotFoundException e) {
			throw new DatabaseConnectionFailedException(
					"Kan niet met de database verbinden(Class not found).");
		} catch (SQLException e) {
			throw new DatabaseConnectionFailedException(
					"Kan niet met de database verbinden(SQL error).");
		} catch (Exception e) {
			throw new DatabaseConnectionFailedException(
					"Kan niet met de database verbinden(Unknown error).");

		}
	}

	/**
	 * Sluit de verbinding met de database
	 */
	public static void disconnect() {
		try {
			// Sluit de verbinding
			dbConnection.close();
			dbConnection = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Haal alle locaties op in het warenhuis waar producten in staat
	 * 
	 * @return Lijst met alle locaties
	 * @throws DatabaseConnectionFailedException
	 */
	public static ArrayList<Location> getAllOccupiedLocations()
			throws DatabaseConnectionFailedException {
		// Geen verbinding? Doe niks
		if (dbConnection == null)
			return null;

		// List om de locaties in op te slaan
		ArrayList<Location> locs = new ArrayList<Location>();

		try {
			// Haal alle locaties op uit database en voeg toe aan lijst
			Statement st = dbConnection.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM products");
			while (rs.next()) {
				int x = rs.getInt("x");
				int y = rs.getInt("y");
				locs.add(new Location(x, y));
			}
		} catch (SQLException e) {
			throw new DatabaseConnectionFailedException(
					"Kan niet met de database verbinden(SQL error).");
		}
		return locs;
	}

	/**
	 * Vul informatie van een product aan vanuit de database
	 * 
	 * @param product
	 * @throws ProductNotFoundException
	 */
	public static void getProductDatabaseInfo(Product product)
			throws ProductNotFoundException {
		try {
			// Haal de informatie over dit product op en sla dit op in de product class
			Statement st = dbConnection.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT * FROM products WHERE artnr="
							+ product.getId() + " ORDER BY y ASC LIMIT 1");

			if (rs.next()) {
				int x = rs.getInt("x");
				int y = rs.getInt("y");
				int size = rs.getInt("size");

				product.setLocation(new Location(x, y));
				product.setSize(size);
			} else {
				throw new ProductNotFoundException("Product niet gevonden");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductNotFoundException(
					"Er kwam een probleem voor tijdens het verbinden met de database");
		}
	}
}
