/**
 * @author Tim Potze
 * @date 3 mei
 */
package tspAlgorithm;

import java.util.ArrayList;

import order.Product;

public class BruteForce implements TSPAlgorithm {
	public static String name = "Brute Force";

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ArrayList<Product> calculateRoute(ArrayList<Product> products) {

		ArrayList<Product> tmp = new ArrayList<Product>();
		ArrayList<Product> fastest = null;
		float length = 0.0f;
		
		if (products.size() == 0)
			return tmp;

		tmp.add(products.get(0));

		if (products.size() == 1)
			return tmp;

		for(ArrayList<Product> route : permute(products, tmp))
		{
			float currentLength = 0;
			for(int i=1;i<route.size();i++)
			{
				currentLength += route.get(i).getLocation().getDistanceTo(route.get(i - 1).getLocation());
			}
			
			if(fastest == null || currentLength < length)
			{
				fastest = route;
				length = currentLength;
			}
		}
		
		return fastest;
	}

	private ArrayList<ArrayList<Product>> permute(ArrayList<Product> origin,
			ArrayList<Product> victim) {
		
		ArrayList<ArrayList<Product>> dest = new ArrayList<ArrayList<Product>>();
		
		for (int i = 0; i < victim.size() + 1; i++) {
			ArrayList<Product> tmp = new ArrayList<Product>(victim);
			
			tmp.add(i, origin.get(victim.size()));
			
			if(tmp.size() == origin.size())
				dest.add(tmp);
			else
				dest.addAll(permute(origin, tmp));
		}
		return dest;
	}


}
