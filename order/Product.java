package order;

import asrs.Database;
import asrs.ProductNotFoundException;

/**
 * Deze classe bevat informatie over een product
 * @author timpotze
 *
 */
public class Product {
	/**
	 * Artikelnummer
	 */
	private int id;
	
	/**
	 * Beschrijving van het product
	 */
	private String description;
	
	/**
	 * Prijs van het product
	 */
	private float price;
	
	/**
	 * De status van het product
	 */
	private String status;
	
	/**
	 * Het formaat van het product
	 */
	private int size;
	
	/**
	 * De locatie van het product in het magazijn
	 */
	private Location location;

	/**
	 * 
	 * @param id artikelnummer van dit product
	 * @param description Beschrijving van dit product
	 * @param price Prijs van het product
	 * @throws ProductNotFoundException Wordt naar je toe geworpen wanneer het product niet gevonden is
	 */
	public Product(int id, String description, float price) throws ProductNotFoundException {
		//Sla informatie uit de parameters op in de atributen van de classe
		this.id = id;
		this.description = description;
		this.price = price;
		this.status = "";
		
		//Haal toevoegende informatie op uit de database
		Database.getProductDatabaseInfo(this);
	}
	
	public Product(Location location, int id){
		this.location = location;
		this.id = id;
	}
	
	public Product(int size, int id){
		this.size = size;
		this.id = id;
	}

	/**
	 * Het artikelnummer van het product
	 * @return het artikelnummer
	 */
	public int getId() {
		return id;
	}

	/**
	 * Beschrijving van het product
	 * @return beschrijving van het product
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Verkrijg de prijs van het product
	 * @return de prijs van het product
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * Verkrijg de status van het product
	 * @return de status van het product
	 */ 
	public String getStatus() {
		return status;
	}

	/**
	 * Sla nieuwe status op van het product
	 * @param status de nieuwe status van eht product
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Verkrijg het formaat van het product
	 * @return Het formaat van het product
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Verander het formaat van het product
	 * @param size nieuwe formaat
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Verkrijg de locatie van het product
	 * @return de loctie
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Verander de locatie van het product
	 * @param location zet de locatie van het product
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * String representatief voor dit Product
	 */
	@Override
	public String toString() {
		return "Product(" + id + ", " + description + " @ " + location + ")";
	}
}
