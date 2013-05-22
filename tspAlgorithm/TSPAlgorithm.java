/**
 * @author Luuk Holleman
 * @date 15 april
 */
package tspAlgorithm;

import java.util.ArrayList;

import order.Product;

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
		ArrayList<Product> filteredProducts = new ArrayList<Product>();
		int width = getEffectiveWarehouseWidth(products);

		for (Product p : products) {
			// splits het magazijn
			int cols = width / numberOfRobots;

			// Als de breedte oneven is, de cols 1 verhogen om het missen van
			// colomen te voorkomen
			if (width % 2 == 1)
				cols++;

			// pak de producten uit het eigen deel van het magazijn
			if (p.getLocation().x >= cols * currentRobot
					&& p.getLocation().x < cols * (currentRobot + 1)) {
				filteredProducts.add(p);
			}
		}

		return filteredProducts;
	}
	
	private int getEffectiveWarehouseWidth(ArrayList<Product> products) {

		int maxX = 0;

		for (Product p : products)
			if (p.getLocation().x > maxX)
				maxX = p.getLocation().x;

		// omdat het magazijn op 0 begint is de breedte 1 hoger
		return maxX + 1;
	}
}
