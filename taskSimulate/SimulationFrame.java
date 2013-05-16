package taskSimulate;

import java.util.ArrayList;

import order.Location;
import order.Product;

import tspAlgorithm.TSPAlgorithm;
import bppAlgorithm.BPPAlgorithm;

public class SimulationFrame {
	private static final int NUMBER_ROBOTS = 2;

	public SimulationFrame(long seed, BPPAlgorithm bpp, TSPAlgorithm tsp) {
		ArrayList<Problem> problems = new ArrayList<Problem>();
		WarehouseTask warehouseTask = new WarehouseTask(seed);

		// Start timer
		warehouseTask.startProcess();

		// Door alle problemen heen lopen
		for (int p = 0; p < warehouseTask.getNumberOfProblems(); p++) {

			// Lijst van de producten per robot
			ArrayList<ArrayList<Product>> products = new ArrayList<ArrayList<Product>>();

			// Initializeren lijst
			for (int r = 0; r < NUMBER_ROBOTS; r++)
				products.add(new ArrayList<Product>());

			// Doorlopen van alle items
			for (int i = 0; i < warehouseTask.getNumberOfItems(p); i++) {

				// Sla de locatie van de items op in een product
				Product product = new Product(new Location(
						warehouseTask.getCoordHorDigit(p, i),
						warehouseTask.getCoordVertDigit(p, i)), i);

				// Bereken welke robot deze moet behandelen
				int robotId = product.getLocation().x
						/ (warehouseTask.maxX / NUMBER_ROBOTS);

				// Voeg toe aan lijst
				products.get(robotId).add(product);
			}
			for (int r = 0; r < NUMBER_ROBOTS; r++){
				//Oplossen volgons algoritme
				products.set(r, tsp.calculateRoute(products.get(r)));
				for(int i = 0 ; i < warehouseTask.getNumberOfItems(p) ; i++){
					warehouseTask.setOrder(p, products.get(r).get(i).getId(), r, i);
				}
			}
				
			
			problems.add(new Problem(products));
		}
		warehouseTask.finishProcess();
		
		//Begin BinPacking algoritmes
		
		BinPackingTask binPackingTask = new BinPackingTask(seed);
		binPackingTask.startProcess();
		
		
	}
}
