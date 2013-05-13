package asrsController;

import java.util.ArrayList;
import java.util.Collection;

import order.Location;
import order.Product;

public class OPRobot {
	public int id;
	public Location loc;
	public Location pixels;
	public Location destination;
	public int load;
	private ArrayList<Product> products;
	public ArrayList<Product> productsOnFork;
	public boolean finished;
//	public ArrayList<Location> locationList = new ArrayList<Location>();
	
	public OPRobot(Location loc, ArrayList<Product> products, int id){
		this.loc = loc;
		pixels = new Location(61 + (loc.x * 20), 1 + ((19 - loc.y) * 20));
		load = 0;
		productsOnFork = new ArrayList<Product>();
		this.id = id;
		this.products = products;
		finished = false;
	}
	
	public OPRobot(Location loc, int id){
		this.loc = loc;
		pixels = loc;
		load = 0;
		productsOnFork = new ArrayList<Product>();
		this.id = id;
	}

	public Product getNextProduct() {
		if (!products.isEmpty()) {
			Product retProduct = products.get(0);
			products.remove(0);
			return retProduct;
		}
		else {
			return null;
		}
	}
	
	public void pickUp(Product product){
		productsOnFork.add(product);
	}
}
