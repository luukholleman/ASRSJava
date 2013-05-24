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
public class BruteForce extends TSPAlgorithm {
	
	private static final float INFINITY = 99999.0f;
	
	// De berekende snelste route tot een betere is gevonden
	private ArrayList<Product> fastest = null;
	private float length = INFINITY;

	
	
	
	public static String name = "Brute Force";
	
	@Override
	public String getName() {
		return BruteForce.name;
	}

	@Override
	public ArrayList<Product> calculateRoute(ArrayList<Product> products,
			int numberOfRobots, int currentRobot) {
		ArrayList<Product> splittedProducts = splitOrder(products, numberOfRobots, currentRobot);
		
		if(splittedProducts.size() > 11) {
			Greedy greedy = new Greedy();

			// we hebben greedy als basis nodig en dus laten we greedy ook de
			// initele route bepalen
			return greedy.calculateRoute(splittedProducts, 1, 0);
		}
				
		Steinhaus sh = new Steinhaus();
		
		ArrayList<Location> locations = new ArrayList<Location>();
		
		for(Product p : splittedProducts)
			locations.add(p.getLocation());
		
		ArrayList<Location> fastest = sh.getShortestPathForLocation(locations, new Location(0, 0));
		
		ArrayList<Product> newProducts = new ArrayList<Product>();
		
		for(Location location : fastest)
			for(Product p : splittedProducts)
				if(p.getLocation() == location)
					newProducts.add(p);
		
		return newProducts;
	}
		
		
//
//		fastest = new ArrayList<Product>();
//		
//		// Als de lijst leeg is, geef deze terug
//		if (splittedProducts.size() == 0)
//			return products;
//
//		// Als er een product is in de lijst, geef deze terug
//		if (splittedProducts.size() == 1)
//			return products;
//
//		// Tijdelijke lijst met alleen het eerste product
//		ArrayList<Product> tmp = new ArrayList<Product>();
//		tmp.add(splittedProducts.get(0));
//
//		// Ga door alle routes in de permutaties heen
//		permute(splittedProducts, tmp);
//		
//		return fastest;

	/**
	 * Bereken alle routes door een product uit de originele lijst toe te voegen
	 * aan de huidige lijst op elke mogelijke locatie in de lijst
	 * 
	 * @param origin
	 *            orginele lijst van producten
	 * @param current
	 *            de huidige lijst van product dat berekend is, maar nog geen
	 *            compleet pad is(niet zo lang als origin)
	 */
	private void permute(ArrayList<Product> origin, ArrayList<Product> current) {

		// Voeg een product toe op elke mogelijke locatie in de lijst
		for (int i = 0; i <= current.size(); i++) {
			// Maak een copie van de huidige lijst
			ArrayList<Product> tmp = new ArrayList<Product>(current);

			// Voeg een product toe aan de lijst, namenlijk het volgende product
			// uit de originele lijst
			tmp.add(i, origin.get(current.size()));
			
			// Als de nieuwe lijst alle producten bevat, geef deze terug,
			// anders, herhaal deze functie
			if (tmp.size() == origin.size()) {
				float currentLength = 0;
				for (int j = 1; j < tmp.size(); j++)
					currentLength += tmp.get(j).getLocation().getDistanceTo(tmp.get(j - 1).getLocation());

				// Als er nog geen snelste route is of deze route korter is dan de
				// vorige korste route, sla deze route op
				if (currentLength < length) {
					fastest = tmp;
					length = currentLength;
				}
			}
			else
				permute(origin, tmp);
		}
	}

}
