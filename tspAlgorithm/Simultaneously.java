package tspAlgorithm;

import java.util.ArrayList;
import java.util.Collections;

import order.Location;
import order.Product;

/**
 * Bereken de korste route door om en om per robot het dichts bijzijnde product
 * te zoeken (
 * 
 * @author timpotze
 * 
 */
public class Simultaneously extends TSPAlgorithm {

	private static final float INFINITY = 99999.0f;
	private static final String NAME = "Simultaneous";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public ArrayList<Product> calculateRoute(ArrayList<Product> products,
			int numberOfRobots, int currentRobot) {

		// Sla alle producten op in een nieuwe array
		ArrayList<Product> allProducts = new ArrayList<Product>(products);

		// Lijst met producten dat het dat de huidige robot moet gaan doen
		ArrayList<Product> path = new ArrayList<Product>();

		// Totale pad lengtes
		ArrayList<Float> robotPathLengths = new ArrayList<Float>();

		// Huidige locatie van de robots
		ArrayList<Location> robotLocations = new ArrayList<Location>();

		// Vul de array
		for (int i = 0; i < numberOfRobots; i++) {
			robotPathLengths.add(0.0f);
			robotLocations.add(new Location(-1 + 21 * i, 0));
		}

		// Verdeel alle producten
		while (allProducts.size() > 0) {
			// Bereken welke robot de kortste route heeft afgelegt
			Float shortest = Collections.min(robotPathLengths);
			int robotId = robotPathLengths.indexOf(shortest);

			// Verkrijg het dichts bijzijnde product
			Product closestProduct = getClosestProduct(allProducts,
					robotLocations.get(robotId));

			// Voeg deze toe aan totale lengthe van de robot
			robotPathLengths.set(
					robotId,
					robotPathLengths.get(robotId)
							+ robotLocations.get(robotId).getDistanceTo(
									closestProduct.getLocation()));

			// Sla deze nieuwe positie op
			robotLocations.set(robotId, closestProduct.getLocation());

			// Product is niet meer nodig, gooi uit lijst
			allProducts.remove(closestProduct);

			// Als we op zoek zijn naar de route van deze robot
			if (currentRobot == robotId)
				path.add(closestProduct);
		}

		// Geef het pad van de huidige robot terug
		return path;
	}

	/**
	 * Vind het product dat het dichts bij de locatie is
	 * 
	 * @param products
	 *            de producten om in te zoeken
	 * @param location
	 *            locatie om vandaan te meten
	 * @return
	 */
	private Product getClosestProduct(ArrayList<Product> products,
			Location location) {
		float closestDistance = INFINITY;
		Product closestProduct = null;

		// Ga door alle producten heen en vind de dichtste bij
		for (Product product : products)
			if (location.getDistanceTo(product.getLocation()) < closestDistance)
			{
				closestDistance = location.getDistanceTo(product.getLocation());
				closestProduct = product;
			}
		return closestProduct;
	}
}
