/**
 * @author Tim Potze
 * @date 3 mei
 */
package tspAlgorithm;

import java.util.ArrayList;

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
		products = splitOrder(products, numberOfRobots, currentRobot);

		// Als de lijst leeg is, geef deze terug
		if (products.size() == 0)
			return products;

		// Als er een product is in de lijst, geef deze terug
		if (products.size() == 1)
			return products;

		// Tijdelijke lijst met alleen het eerste product
		ArrayList<Product> tmp = new ArrayList<Product>();
		tmp.add(products.get(0));

		// Ga door alle routes in de permutaties heen
		permute(products, tmp);

		return fastest;
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
		for (int i = 0; i < current.size() + 1; i++) {
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
