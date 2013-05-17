package taskSimulate;

import java.util.ArrayList;

import order.Product;

public class TravelingSalesmanProblem {
	private ArrayList<ArrayList<Product>> products;
	
	public TravelingSalesmanProblem(ArrayList<ArrayList<Product>> products){
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
	
	public ArrayList<ArrayList<Product>> getProducts() {
		return products;
	}
	
	public void setProducts(ArrayList<ArrayList<Product>> products) {
		this.products = products;
	}
}
