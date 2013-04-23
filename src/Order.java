import java.util.ArrayList;
import java.util.Date;

public class Order {
	private Date date;
	private float totalPrice;
	private ArrayList<Product> products;
	private Customer customer;

	public Order(Date date, float totalPrice, Customer customer) {
		this.date = date;
		this.totalPrice = totalPrice;
		this.products = new ArrayList<Product>();
		this.customer = customer;
	}

	public Date getDate() {
		return date;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void addProduct(Product product) {
		products.add(product);
	}

	public Customer getCustomer() {
		return customer;
	}

}
