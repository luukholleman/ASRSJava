/**
 * @author Luuk Holleman
 * @date 15 april
 */
package tspAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import order.Location;
import order.Product;

public class Random extends TSPAlgorithm {
	/**
	 * De naam
	 */
	public static String name = "Random";
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public ArrayList<Product> calculateRoute(ArrayList<Product> products,
			int numberOfRobots, int currentRobot) {
		
		// een lekker split ijsje
		ArrayList<Product> splittedProducts = splitOrder(products, numberOfRobots, currentRobot);
		
		Collections.shuffle(splittedProducts);
		 
		return splittedProducts;
	}
}
