/**
 * @author Luuk Holleman
 * @date 15 april
 */
package tspAlgorithm;

import java.util.ArrayList;

import asrs.Product;

public interface TSPAlgorithm {
	public abstract String getName();
	
	public abstract ArrayList<Product> calculateRoute(ArrayList<Product> products);
}