/**
 * @author Luuk Holleman
 * @date 15 april
 */
package tspAlgorithm;

import java.util.ArrayList;

import order.Product;


public interface TSPAlgorithm {
	public abstract String getName();
	
	/**
	 * Calculates best route for the products that should be on-route
	 * @param products the products that have to be picked up
	 * @return list of products in the proper order
	 */
	public abstract ArrayList<Product> calculateRoute(ArrayList<Product> products);
}
