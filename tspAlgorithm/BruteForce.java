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
	public ArrayList<Product> calculateRoute(ArrayList<Product> products, int numberOfRobots, int currentRobot) {
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
			//bereken de totale lengte van de route
			float currentLength = 0;
			for (int i = 1; i < route.size(); i++) {
				currentLength += route.get(i).getLocation()
						.getDistanceTo(route.get(i - 1).getLocation());
			}

			// Als er nog geen snelste route is of deze route korter is dan de vorige korste route, sla deze route op
			if (fastest == null || currentLength < length) {
				fastest = route;
				length = currentLength;
			}
		}

		return fastest;
	}

	/**
	 * Calculate all routes for the victim by adding one product from the original list
	 * @param origin original list of products
	 * @param victim the current list of items that are not yet a complete path
	 * @return all available routes for the victim and one added product from the original list
	 */
	private ArrayList<ArrayList<Product>> permute(ArrayList<Product> origin,
			ArrayList<Product> victim) {

		//Stores all the available routes 
		ArrayList<ArrayList<Product>> dest = new ArrayList<ArrayList<Product>>();

		//Add a product to the victim's list
		for (int i = 0; i < victim.size() + 1; i++) {
			//Copy the victim's list
			ArrayList<Product> tmp = new ArrayList<Product>(victim);

			//Add the product to the victim's list where id == the length of victim
			tmp.add(i, origin.get(victim.size()));
			
			//If the new size contains all the products from the original list, add this list to the result
			if (tmp.size() == origin.size())
				dest.add(tmp);
			//Else, run permute again to calculate the options with this new victim
			else
				dest.addAll(permute(origin, tmp));
		}
		return dest;
	}

}
