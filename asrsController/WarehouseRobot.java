package asrsController;

import java.util.ArrayList;

import order.Location;
import order.Product;

public class WarehouseRobot {
	public int id;
	public Location loc;
	public Location pixels;
	public Location destination;
	public int load;
	private ArrayList<Product> products;
	public ArrayList<Product> productsOnFork;
	public boolean finished;
	
	public WarehouseRobot(Location loc, ArrayList<Product> products, int id){
		this.loc = loc;
		pixels = new Location(61 + (loc.x * 20), 1 + ((19 - loc.y) * 20));
		load = 0;
		productsOnFork = new ArrayList<Product>();
		this.id = id;
		this.setProducts(products);
		finished = false;
		System.out.println("De producten in deze robot hebben de groote: ");
		for(Product product : products){
			System.out.println(Integer.toString(product.getSize()));
		}
	}
	
	public WarehouseRobot(Location loc, int id){
		this.loc = loc;
		pixels = loc;
		load = 0;
		productsOnFork = new ArrayList<Product>();
		this.id = id;
	}

	public Product getNextProduct() {
		if (!getProducts().isEmpty()) {
			Product retProduct = getProducts().get(0);
			getProducts().remove(0);
			return retProduct;
		}
		else {
			return null;
		}
	}
	
	public void pickUp(Product product){
		productsOnFork.add(product);
	}

	/**
	 * @return the products
	 */
	public ArrayList<Product> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
}
