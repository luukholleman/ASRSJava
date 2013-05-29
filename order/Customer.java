package order;

/**
 * Deze classe bevat invormatie over de klant
 * @author Tim
 *
 */
public class Customer {
	
	/**
	 * Klantennummer
	 */
	private int id;
	
	/**
	 * Naam van klant
	 */
	private String name;

	/**
	 * 
	 * @param id klantennummer
	 * @param name naam van klant
	 */
	public Customer(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Verkrijg het klantennummer
	 * @return klantennummer
	 */
	public int getId() {
		return id;
	}

	/**
	 * Verkrijg de naam van de klant
	 * @return naam van de klant
	 */
	public String getName() {
		return name;
	}
}
