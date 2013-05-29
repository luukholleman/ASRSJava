package tspAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import order.Product;

/**
 * Algoritme interface voor de TSP algoritmes
 * @author Tim
 *
 */
public abstract class TSPAlgorithm {
	
	/**
	 * Verkrijg naam van algoritme
	 * @return
	 */
	public abstract String getName();

	/**
	 * Bereken de snelste route
	 * @param products producten om op te halen
	 * @param numberOfRobots het aantal robots
	 * @param currentRobot de huidige robot voor wie het pad moet worden berekend
	 * @return de route
	 */
	public abstract ArrayList<Product> calculateRoute(
			ArrayList<Product> products, int numberOfRobots, int currentRobot);

	/**
	 * Splits de order efficient voor het aantal robots megegeven
	 * @param products produducten om op te halen
	 * @param numberOfRobots het aantal robots
	 * @param currentRobot de robot voor wie je aan het splitsen bent
	 * @return het gesplitste producten
	 */
	protected ArrayList<Product> splitOrder(ArrayList<Product> products,
			int numberOfRobots, int currentRobot) {

		// Sla
		ArrayList<Product> allProducts = new ArrayList<Product>(products);

		Collections.sort(allProducts, new Comparator<Product>() {
			public int compare(Product one, Product two) {
				return ((Integer) (one.getLocation().x)).compareTo(two
						.getLocation().x);
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
