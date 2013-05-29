package asrsController;

import java.util.ArrayList;

import productInfo.Location;
import productInfo.Product;

public class Robot {
	private Location currentLocation;
	private ArrayList<Product> products;
	public ArrayList<Product> productsOnFork;
	
	public Robot(Location startLocation, ArrayList<Product> products){
		setCurrentLocation(startLocation);		
		this.products = products;
		productsOnFork = new ArrayList<Product>();
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	public Product getNextProduct(){
		if(products.size() ==0){
			return null;
		}
		Product nextProduct = products.get(0);
		products.remove(0);
		productsOnFork.add(nextProduct);
		return nextProduct;
	}
}
