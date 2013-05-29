/**
 * @author Tim Potze
 * @date 15 april
 */
package tspAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import productInfo.Product;

public abstract class TSPAlgorithm {
	public abstract String getName();

	/**
	 * Calculates best route for the products that should be on-route
	 * 
	 * @param products
	 *            the products that have to be picked up
	 * @return list of products in the proper order
	 */
	public abstract ArrayList<Product> calculateRoute(
			ArrayList<Product> products, int numberOfRobots, int currentRobot);

	protected ArrayList<Product> splitOrder(ArrayList<Product> products,
			int numberOfRobots, int currentRobot) {

		// Sla
		ArrayList<Product> allProducts = new ArrayList<Product>(products);

		Collections.sort(allProducts, new Comparator<Product>() {
			public int compare(Product one, Product two) {
				return ((Integer) (one.getLocation().getX())).compareTo(two
						.getLocation().getX());
			}
		});

		// Verdeel de producten over de robots
		int productsPerRobot = allProducts.size() / numberOfRobots;

		// Berenken de start en begin indexen
		int startIndex = currentRobot * productsPerRobot;
		int endIndex = (currentRobot + 1) * productsPerRobot;

		// Als er een oneven aantal producten zijn, tel er een bij op
		if (allProducts.size() % numberOfRobots == 1
				&& currentRobot == numberOfRobots - 1)
			endIndex++;

		return new ArrayList<Product>(allProducts.subList(startIndex, endIndex));
	}
}
