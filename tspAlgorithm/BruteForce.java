package tspAlgorithm;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import order.Location;
import order.Product;

/**
 * Brute force algoritme om de allerbeste route te berekenen
 * 
 * @author timpotze
 * 
 */
public class BruteForce extends TSPAlgorithm {
	
	/**
	 * Het oneindige
	 */
	private static final float INFINITY = 99999.0f;
	
	public static final String NAME = "Brute Force";
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public ArrayList<Product> calculateRoute(ArrayList<Product> products,
			int numberOfRobots, int currentRobot) {
		ArrayList<Product> splittedProducts = splitOrder(products, numberOfRobots, currentRobot);
		
		// steinhaus om alle combinaties te maken
		Steinhaus sh = new Steinhaus();

		// plaats voor alle locaties van de producten
		ArrayList<Location> locations = new ArrayList<Location>();
		
		// we strippen alle locaties van de producten
		for(Product p : splittedProducts)
			locations.add(p.getLocation());
		
		// laat de kortste berekenen
		ArrayList<Location> fastest = sh.getShortestPathForLocation(locations, new Location(0, 0));
		
		// nieuwe lijst met producten
		ArrayList<Product> newProducts = new ArrayList<Product>();
		
		// we zoeken weer het product bij de locaties
		for(Location location : fastest)
			for(Product p : splittedProducts)
				if(p.getLocation() == location)
					newProducts.add(p);
		
		return newProducts;
	}
}
