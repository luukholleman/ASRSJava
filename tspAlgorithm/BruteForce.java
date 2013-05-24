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
		
		// als er meer dan 11 producten moet worden berekend is het niet meer haalbaar met bruteforce, greedy is het alternatief
		if(splittedProducts.size() > 12) {
			Greedy greedy = new Greedy();

			return greedy.calculateRoute(splittedProducts, 1, 0);
		}
		
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
