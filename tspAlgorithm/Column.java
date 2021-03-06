package tspAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import productInfo.Product;

/**
 * TSP algoritme die per colom alle producten langs gaat
 * @author Tim
 *
 */
public class Column extends TSPAlgorithm {
	private static final String NAME = "Column";

	@Override
	public String getName() {
		return NAME;
	}

	public ArrayList<Product> calculateRoute(ArrayList<Product> products,
			int numberOfRobots, int currentRobot) {
		// Splits de producten per robot
		products = splitOrder(products, numberOfRobots, currentRobot);

		// Arraylist om de route in op te slaan
		ArrayList<Product> route = new ArrayList<Product>();

		// Welke col om te vinden in deze ronde
		int x = 0;
		
		//Hoeveelste gevonden colom dit is
		int foundColumn = 0;
		
		// Ga door tot alle producten verwerkt zijn
		while (products.size() > 0) {
			// Sla alle produten uit deze col indeze lijst op
			ArrayList<Product> column = new ArrayList<Product>();

			//Ga door alle producten heen, en vind de juisten
			for (Product product : products)
				if (product.getLocation().getX() == x)
					column.add(product);

			//Verwijder de gevonden producten
			products.removeAll(column);
			
			if(column.size() > 0)
			{
				//Sorteer de colom op y as coordinaat
				Collections.sort(column, new Comparator<Product>() {
					public int compare(Product one, Product two) {
						return ((Integer)(one.getLocation().getY())).compareTo(two.getLocation().getY());
					}
				});
				
				//Als de colom de onevense in de lijst is is, flip em
				if(foundColumn % 2 == 0)
					Collections.reverse(column);
				
				for(Product product : column)
					route.add(product);
				
				foundColumn++;
			}
			x++;
		}
		

		return route;
	}
}
