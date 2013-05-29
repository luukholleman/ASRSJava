package asrsController;

import java.util.ArrayList;

import asrs.OrderPickingPanel;

import order.Location;
import order.Product;
/** 
 * De robot van het warenhuis
 * 
 * @author Bas
 */
public class WarehouseRobot {
	/**
	 * De ID van de robot wordt opgeslagen zodat in de simulatie een
	 * soortgelijke robot kan worden gebruikt zonder dat ze tegelijkertijd
	 * dezelfde informatie hebben
	 */
	public int id;
	/**
	 * De locatie van de robot in het warenhuis
	 */
	public Location location;
	/**
	 * De pixels van de robot worden alleen gebruikt voor het tekenen in de
	 * simulatie
	 */
	public Location pixels;
	/**
	 * De huidige bestemming van de robot
	 */
	public Location destination;
	/**
	 * De lading van de robot in aantal producten
	 */
	public int load;
	/**
	 * De ArrayList van alle producten die moeten worden opgehaald
	 */
	private ArrayList<Product> products;
	/**
	 * De ArrayList van alle producten die zijn opgehaald
	 */
	public ArrayList<Product> productsOnFork;
	/**
	 * De boolean die aangeeft of de robot klaar is of niet.
	 */
	public boolean finished;

	/**
	 * Constructor voor de simulatie
	 * 
	 * @param location
	 * @param products
	 * @param id
	 */
	public WarehouseRobot(Location location, ArrayList<Product> products, int id) {
		this.location = location;
		
		//De lading begint op 0
		load = 0;
		
		//De fork is op het begin altijd leeg
		productsOnFork = new ArrayList<Product>();
		
		this.id = id;
		this.setProducts(products);
		finished = false;
	}

	/**
	 * Constructor voor de Task
	 * 
	 * @param location
	 * @param id
	 */
	public WarehouseRobot(Location location, int id) {
		this.location = location;
		pixels = location;
		load = 0;
		productsOnFork = new ArrayList<Product>();
		this.id = id;
	}

	/**
	 * Haal het volgende product uit de producten lijst en haal deze daarna uit
	 * de lijst.
	 * 
	 * @author Bas
	 */
	public Product getNextProduct() {
		if (!getProducts().isEmpty()) {
			Product retProduct = getProducts().get(0);
			getProducts().remove(0);
			return retProduct;
		} else {
			return null;
		}
	}

	/**
	 * Geeft aan of er nog producten voor deze robot zijn
	 * @return Waarheid
	 */
	public Boolean hasNextProduct() {
		return getProducts().size() > 0;
	}
	/**
	 * Legt het product op de fork
	 * 
	 * @param product
	 * @author Bas
	 */
	public void pickUp(Product product) {
		productsOnFork.add(product);
	}

	/**
	 * @return the products
	 */
	public ArrayList<Product> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
}
