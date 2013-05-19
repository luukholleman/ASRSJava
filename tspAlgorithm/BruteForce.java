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
	public static String name = "Brute Force";

	@Override
	public String getName() {
		return BruteForce.name;
	}

	@Override
	public ArrayList<Product> calculateRoute(ArrayList<Product> products,
			int numberOfRobots, int currentRobot) {
		products = splitOrder(products, numberOfRobots, currentRobot);

		// De berekende snelste route tot een betere is gevonden
		ArrayList<Product> fastest = null;
		float length = 0.0f;

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
		for (ArrayList<Product> route : permute(products, tmp)) {
			// bereken de totale lengte van de route
			float currentLength = 0;
			for (int i = 1; i < route.size(); i++) {
				currentLength += route.get(i).getLocation()
						.getDistanceTo(route.get(i - 1).getLocation());
			}

			// Als er nog geen snelste route is of deze route korter is dan de
			// vorige korste route, sla deze route op
			if (fastest == null || currentLength < length) {
				fastest = route;
				length = currentLength;
			}
		}

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
	 * @return alle mogelijke product volgordes berekend
	 */
	private ArrayList<ArrayList<Product>> permute(ArrayList<Product> origin,
			ArrayList<Product> current) {

		// Lijst om alle mogelijke volgordes op te slaan
		ArrayList<ArrayList<Product>> dest = new ArrayList<ArrayList<Product>>();

		// Voeg een product toe op elke mogelijke locatie in de lijst
		for (int i = 0; i < current.size() + 1; i++) {
			// Maak een copie van de huidige lijst
			ArrayList<Product> tmp = new ArrayList<Product>(current);

			// Voeg een product toe aan de lijst, namenlijk het volgende product
			// uit de originele lijst
			tmp.add(i, origin.get(current.size()));

			// Als de nieuwe lijst alle producten bevat, geef deze terug,
			// anders, herhaal deze functie
			if (tmp.size() == origin.size())
				dest.add(tmp);
			else
				dest.addAll(permute(origin, tmp));
		}
		return dest;
	}

}
