package order;
import java.util.ArrayList;
import java.util.Date;

/**
 * Deze classe bevat informatie over de order
 * @author Tim
 *
 */
public class Order {
	
	/**
	 * Datum van bestelling
	 */
	private Date date;
	
	/**
	 * De totaalprijs
	 */
	private float totalPrice;
	
	/**
	 * De producten die op deze bestelling staan
	 */
	private ArrayList<Product> products;
	
	/**
	 * Instantie van Customer met klantinformatie
	 */
	private Customer customer;

	/**
	 * 
	 * @param date Datum van de bestelling
	 * @param totalPrice Totaalprijs
	 * @param customer Instantie van Customer met klantinformatie
	 */
	public Order(Date date, float totalPrice, Customer customer) {
		
		//Bewaar parameters in de atributen
		this.date = date;
		this.totalPrice = totalPrice;
		this.products = new ArrayList<Product>();
		this.customer = customer;
	}

	/**
	 * Verkrijg de datum van de bestelling
	 * @return de datum
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Verkrijg de totaalprijs
	 * @return de totaalprijs
	 */
	public float getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Verkrijg de producten die in deze order staan
	 * @return de producten als een ArrayList<Product>
	 */
	public ArrayList<Product> getProducts() {
		return products;
	}

	/**
	 * Voeg een product toe aan deze order
	 * @param product
	 */
	public void addProduct(Product product) {
		products.add(product);
	}

	/**
	 * Verkrijg informatie over de klant van deze order 
	 * @return
	 */
	public Customer getCustomer() {
		return customer;
	}

}
