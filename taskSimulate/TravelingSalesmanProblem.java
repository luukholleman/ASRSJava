package taskSimulate;

import java.util.ArrayList;

import order.Product;

/**
 * Resultaten van het traveling salesman problem
 * @author Tim
 *
 */
public class TravelingSalesmanProblem {
	
	/**
	 * Alle robots met met producten die opgehaald moeten worden
	 */
	private ArrayList<ArrayList<Product>> problem;
	
	/**
	 * 
	 * @param problem De problemen
	 */
	public TravelingSalesmanProblem(ArrayList<ArrayList<Product>> problem){
		this.problem = problem;
	}
	
	@Override
	public String toString(){
		
		//Voeg alle producten terug alle robots
		String string = "";
		for(ArrayList<Product> robot : problem)
		{
			string += "==================================";
			string += "ROBOT " + problem.indexOf(robot) + ":\n";
			for (Product product : robot)
				string += product + "\n";
		}
			
		return string;
	}
	
	public ArrayList<ArrayList<Product>> getProblem() {
		return problem;
	}
	
	public void setRobots(ArrayList<ArrayList<Product>> problem) {
		this.problem = problem;
	}
	
	
}
