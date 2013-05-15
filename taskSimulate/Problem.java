package taskSimulate;

import java.util.ArrayList;

import order.Product;

public class Problem {
	private ArrayList<ArrayList<Product>> products;
	
	public Problem(ArrayList<ArrayList<Product>> products){
		this.products = products;
	}
	@Override
	public String toString(){
		String string = "";
		for(ArrayList<Product> robot : products)
			for (Product product : robot)
				string += product.getLocation() + "\n";
			
		return string;
	}
}
