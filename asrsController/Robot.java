package asrsController;

import java.util.ArrayList;

import order.Location;
import order.Product;

public class Robot {
	private Location currentLocation;
	private ArrayList<Product> products;
	
	public Robot(Location startLocation, ArrayList<Product> products){
		setCurrentLocation(startLocation);		
		this.products = products;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}
}
