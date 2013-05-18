package taskSimulate;

import java.util.ArrayList;

import order.Product;

public class TravelingSalesmanProblem {
	private ArrayList<ArrayList<Product>> problem;
	
	public TravelingSalesmanProblem(ArrayList<ArrayList<Product>> problem){
		this.problem = problem;
	}
	
	@Override
	public String toString(){
		String string = "";
		for(ArrayList<Product> robot : problem)
			for (Product product : robot)
				string += product.getLocation() + "\n";
			
		return string;
	}
	
	public ArrayList<ArrayList<Product>> getProblem() {
		return problem;
	}
	
	public void setRobots(ArrayList<ArrayList<Product>> problem) {
		this.problem = problem;
	}
	
	
}
