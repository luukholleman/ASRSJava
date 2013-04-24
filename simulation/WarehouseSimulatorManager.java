package simulation;

import java.util.ArrayList;

import asrs.Location;

import tspAlgorithm.TSPAlgorithm;
import order.Product;

public class WarehouseSimulatorManager {
	private ArrayList<Product> route;
	
	public void run(TSPAlgorithm algorithm){
		//Ophalen XML order
		//NOT YET IMPLANTED
		ArrayList<Product> products = new ArrayList<Product>();
		
		Location testlocation = new Location(1,1);
		Product testproduct = new Product(1, "fiets", 1, 1, testlocation);
		
		products.add(testproduct);
		
		this.route = algorithm.calculateRoute(products);
		Location location= null;
		for(Product product : route){
			location = product.getLocation();
			System.out.println((location.x)+ " " + (location.y));
		}
	}

	public ArrayList<Product> getRoute() {
		return route;
	}

	public void setRoute(ArrayList<Product> route) {
		this.route = route;
	}
}
