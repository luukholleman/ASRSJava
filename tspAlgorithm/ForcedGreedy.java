/**
 * @author Tim Potze
 * @date 3 mei
 */
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
public class ForcedGreedy extends TSPAlgorithm {
	private static final int INFINITY = 99999;

	// de locatie van de robot, de robot start op 0, 0
	private Location location = new Location(0, 0);
	
	public static String name = "Forced Greedy";

	/**
	 * De berekende route, wordt gevuld met objecten uit products
	 */
	private ArrayList<Product> route = new ArrayList<Product>();

	
	@Override
	public String getName() {
		return ForcedGreedy.name;
	}

	@Override
	public ArrayList<Product> calculateRoute(ArrayList<Product> products,
			int numberOfRobots, int currentRobot) {
		ArrayList<Product> splittedProducts = splitOrder(products, numberOfRobots, currentRobot);
		
		// als er meer dan 11 producten moet worden berekend is het niet meer haalbaar met bruteforce, greedy is het alternatief
		if(splittedProducts.size() < 10) {
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

		this.route = new ArrayList<Product>();
		
		while (nextNode(splittedProducts));

		return this.route;
		
	}

	/**
	 * Berekent het dichtsbijzijnde product verwijderd het product uit products
	 * zodat het niet meer meegenomen wordt
	 * 
	 * @param products
	 * @return product
	 */
	private boolean nextNode(ArrayList<Product> products) {
		// de arraylist is leeg, niks te berekenen. return false
		if (products.size() == 0)
			return false;
		float minDistance = INFINITY;
		Product minProduct = null;

		// we lopen nu elk product af en berekenen de afstand. de kortste wordt
		// opgeslagen
		for (Product product : products) {
			float distance = location.getDistanceTo(product.getLocation());

			// als minDistance is de eerste keer, dan moet hij altijd geset
			// worden.
			// de andere statement is als de net berekende distance korter is
			// dan de vorige
			if (minDistance > distance) {
				minDistance = distance;
				minProduct = product;
			}
		}

		// de robot is nu op de locatie van dit product, update de locatie
		location = minProduct.getLocation();

		// we hebben het dichtsbijzijnde product, voeg hem toe aan de route,
		// verwijderen van de nog te berekenen producten
		route.add(minProduct);
		products.remove(minProduct);

		// we hebben een node! return true
		return true;

	}
	

}
