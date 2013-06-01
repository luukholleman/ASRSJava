package asrsController;

import gui.OrderPickingPanel;

import java.util.ArrayList;

import productInfo.Location;
import productInfo.Product;

public class WarehouseRobot {
	/**
	 * De ID van de robot wordt opgeslagen zodat in de simulatie een
	 * soortgelijke robot kan worden gebruikt zonder dat ze tegelijkertijd
	 * dezelfde informatie hebben
	 */
	private int id;
	/**
	 * De locatie van de robot in het warenhuis
	 */
	private Location location;
	/**
	 * De pixels van de robot worden alleen gebruikt voor het tekenen in de
	 * simulatie
	 */
	private Location pixels;
	/**
	 * De huidige bestemming van de robot
	 */
	private Location destination;
	/**
	 * De lading van de robot in aantal producten
	 */
	private int load;
	/**
	 * De ArrayList van alle producten die moeten worden opgehaald
	 */
	private ArrayList<Product> products;
	/**
	 * De ArrayList van alle producten die zijn opgehaald
	 */
	private ArrayList<Product> productsOnFork;
	/**
	 * De boolean die aangeeft of de robot klaar is of niet.
	 */
	private boolean finished;

	/**
	 * Constructor voor de simulatie
	 * 
	 * @param location
	 * @param products
	 * @param id
	 */
	public WarehouseRobot(Location location, ArrayList<Product> products, int id) {
		this.setLocation(location);
		
		//De lading begint op 0
		setLoad(0);
		
		//De fork is op het begin altijd leeg
		setProductsOnFork(new ArrayList<Product>());
		
		this.setId(id);
		this.setProducts(products);
		setFinished(false);
	}

	/**
	 * Constructor voor de Task
	 * 
	 * @param location
	 * @param id
	 */
	public WarehouseRobot(Location location, int id) {
		this.setLocation(location);
		setPixels(location);
		setLoad(0);
		setProductsOnFork(new ArrayList<Product>());
		this.setId(id);
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
		getProductsOnFork().add(product);
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

	/**
	 * @return the productsOnFork
	 */
	public ArrayList<Product> getProductsOnFork() {
		return productsOnFork;
	}

	/**
	 * @param productsOnFork the productsOnFork to set
	 */
	public void setProductsOnFork(ArrayList<Product> productsOnFork) {
		this.productsOnFork = productsOnFork;
	}

	/**
	 * @return the load
	 */
	public int getLoad() {
		return load;
	}

	/**
	 * @param load the load to set
	 */
	public void setLoad(int load) {
		this.load = load;
	}

	/**
	 * @return the destination
	 */
	public Location getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Location destination) {
		this.destination = destination;
	}

	/**
	 * @return the pixels
	 */
	public Location getPixels() {
		return pixels;
	}

	/**
	 * @param pixels the pixels to set
	 */
	public void setPixels(Location pixels) {
		this.pixels = pixels;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * @param finished the finished to set
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}
