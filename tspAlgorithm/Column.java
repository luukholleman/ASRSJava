package tspAlgorithm;

import java.util.ArrayList;
import asrs.Product;
import asrs.Location;


public class Column implements TSPAlgorithm {
	//visibility modifiers + opsomming mag niet
	private ArrayList<Product> route = new ArrayList<Product>();
	private ArrayList<Product> column1 = new ArrayList<Product>(); 
	private ArrayList<Product> column2 = new ArrayList<Product>();
	Location location;
	public static String name = "Column";
	int columnsize;
	
	
	@Override
	public String getName() {
		return name;
	}
	
	
	/**
	 * Berekend een efficiente route op basis van het (zelf bedachte) Column algoritme.
	 * 
	 * @param ArrayList<Product>
	 * @return ArrayList<Product>
	 */
	public ArrayList<Product> calculateRoute(ArrayList<Product> products){
		//Opzoeken van het horizontale verste product
		int xmax = 0;
		for (Product product : products){
			location = product.getLocation();
			if (location.x > xmax){
				xmax = location.x;
			}
		}
		/** Het magazijn  wordt verticaal verdeelt in 2 kolommen
		 *  en daarin worden de producten verdeelt.
		*/  
		columnsize = xmax/2;
		for (Product product : products){
			location = product.getLocation();
			if(location.x <= columnsize){
				column1.add(product);
			}
			else{
				column2.add(product);
			}
		}
		//Daarna wordt alleen nog maar de producten in volgorde van hoogte verdeelt.
		sortColumn(column1, true);
		sortColumn(column2, false);
		
		
		return route;
	}

	/**
	 * Berekend het laagste product
	 * verwijderd het product uit products zodat het niet meer meegenomen wordt
	 * 
	 * @param products
	 * @return product
	 */
	private void sortColumn(ArrayList<Product> products, boolean dir){
		// de arraylist is leeg, niks te berekenen. return zonder iets te doen
		if(products.size() == 0) return;
		Product minProduct = null;
		
		// we lopen nu elk product af en berekenen de hoogte. de laagste wordt opgeslagen
		while(products.size() != 0){
			int ymax = 0;
			int ymin = 0;
			for(Product product : products) {
				location = product.getLocation();
				int yloc = location.y;
				
				if(dir){
					/* als ymin is de eerste keer, dan moet hij altijd geset worden.
					* de andere statement is als de net berekende hoogte korter is dan de vorige
					*/
					if(ymin == 0 || ymin > yloc) {
						ymin = yloc;
						minProduct = product;
					}	
				}
				if(!dir){
					// Zelfde als de vorige, maar in de andere richting
					if(ymax == 0 || ymax < yloc) {
						ymax = yloc;
						minProduct = product;
					}
				}
			}
		
		
		/* we hebben het dichtsbijzijnde product, voeg hem toe aan de route, 
		*  verwijderen van de nog te berekenen producten
		*/
		route.add(minProduct);
		products.remove(minProduct);
		}
	}
}
