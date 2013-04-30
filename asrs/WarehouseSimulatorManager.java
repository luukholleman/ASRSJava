package asrs;

import java.util.ArrayList;

import tspAlgorithm.TSPAlgorithm;
import order.Product;

public class WarehouseSimulatorManager {
	private ArrayList<Product> route;
	
	public void run(OrderPickingSimulatorPanel OPSPanel, TSPAlgorithm algorithm){
		//Ophalen XML order
		//NOT YET IMPLANTED
		ArrayList<Product> products = null;
		
		setRoute(algorithm.calculateRoute(products));
	}

	public ArrayList<Product> getRoute() {
		return route;
	}

	public void setRoute(ArrayList<Product> route) {
		this.route = route;
	}
}
