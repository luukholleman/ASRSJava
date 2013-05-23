package asrsController;

import java.util.ArrayList;

import order.*;
import tspAlgorithm.TSPAlgorithm;
import asrs.*;
import bppAlgorithm.*;

public class ExecutionManager {
	private Main main;
	private Order order;
	private BinManager binManager;
	private Warehouse warehouse;
	private BinPacking binPacking;
	private TSPAlgorithm tspAlgorithm;
	private BPPAlgorithm bppAlgorithm;
	private int width;
	private int height;
	private Boolean useDetectedSize;
	private int load = 0;
	private WarehouseRobot[] robots;
	private ArrayList<Product> bppProducts = new ArrayList<Product>();

	/**
	 * 
	 * @param main
	 * @param order
	 * @param binManager
	 * @param warehouse
	 * @param binPacking
	 * @param tspAlgorithm
	 * @param bppAlgorithm
	 * @param width
	 * @param height
	 * @param useDetectedSize
	 */
	public ExecutionManager(Main main, Order order, BinManager binManager,
			Warehouse warehouse, BinPacking binPacking,
			TSPAlgorithm tspAlgorithm, BPPAlgorithm bppAlgorithm, int width,
			int height, Boolean useDetectedSize) {

		this.main = main;
		this.order = order;
		this.binManager = binManager;
		this.warehouse = warehouse;
		this.binPacking = binPacking;
		this.tspAlgorithm = tspAlgorithm;
		this.bppAlgorithm = bppAlgorithm;
		this.width = width;
		this.height = height;
		this.useDetectedSize = useDetectedSize;

		// maak een array aan met een plek voor elke robot in het warehouse
		robots = new WarehouseRobot[warehouse.getNumberOfRobots()];

		// loop de robots en bepaal de route die de robot moet volgen
		for (int r = 0; r < warehouse.getNumberOfRobots(); r++) {
			//Sorteer de producten volgens het meegegeven algoritme
			ArrayList<Product> products = tspAlgorithm.calculateRoute(order.getProducts(), warehouse.getNumberOfRobots(), r);
			
			// init de robot met zijn eigen startlocatie, producten en id
			robots[r] = new WarehouseRobot(warehouse.getStartLocation(r), products, r);			
		}
	}
	
	/**
	* Start de robot, laat de simulatie lopen of laat de arduino bewegen
	* 
	* @return void
	*/
	public void start() {
		// we moeten elke robot aanspreken en de id's zijn oplopend, de i is dus het robotid
		int i = 0;
		 
		for(WarehouseRobot wr : robots) { 
			//laat de robot het volgende product ophalen
			Product nextProduct = wr.getNextProduct();
			  
			// is er een volgende product? zo ja, haal hem op, de robot wordt aangesproken met het id
			if (nextProduct != null)
				warehouse.retrieveProduct(nextProduct.getLocation(), i);
			  
			// klaar met deze robot, op naar de volgende
			i++;
		}
	}
	
	/**
	 * Wordt opgeroepen door bppArduino, zet de kleur om naar een grootte, of gebruikt het products zijn opgeslagen grootte.
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @return binIndex
	 */
	private void detectedProduct(Byte color) {
		Bin bin;
		if (useDetectedSize) {
			bppProducts.get(0).setSize((int) color);
			bin = bppAlgorithm.calculateBin(bppProducts.get(0),
					binManager.bins);
			
		} else {
			bin = bppAlgorithm.calculateBin(bppProducts.get(0),
					binManager.bins);
		}
		if(bin != null){
			binManager.bins.get(binManager.bins.indexOf(bin)).fill(bppProducts.get(0));
			binPacking.packProduct((byte) binManager.bins.size(), bppProducts.get(0));
		}
		else{
			binPacking.packProduct((byte) binManager.bins.indexOf(bin), bppProducts.get(0));
			bppProducts.remove(0);
		}
	}

	/**
	 * Geeft volgende locatie, tenzij er geen locatie meer is, Dan bringToBinPacker() aanroepen.
	 * 
	 * @param robotId
	 */
	public void pickedUpProduct(int robotId) {
		Product nextProduct = robots[robotId].getNextProduct();
		if (nextProduct != null)
			warehouse.retrieveProduct(nextProduct.getLocation(), robotId);
		else
			warehouse.bringToBinPacker(robotId);
	}

	/**
	 * Wordt aangeroepen nadat de producten af geleverd zijn door de robot.
	 * 
	 * @param robotId
	 */
	public void deliveredProduct(WarehouseRobot robot, Byte color) {
		bppProducts.addAll(robot.productsOnFork);
		robot.productsOnFork.clear();
		System.out.println("Products delivered");
		if(!bppProducts.isEmpty())
			detectedProduct(color);
		else
			warehouse.moveToStart(robot.id);
	}

	// Alle getters
	public Main getMain() {
		return main;
	}

	public Order getOrder() {
		return order;
	}

	public BinManager getBinManager() {
		return binManager;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public BinPacking getBinPacking() {
		return binPacking;
	}

	public TSPAlgorithm getTspAlgorithm() {
		return tspAlgorithm;
	}

	public BPPAlgorithm getBppAlgorithm() {
		return bppAlgorithm;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Boolean getUseDetectedSize() {
		return useDetectedSize;
	}

	public int getLoad() {
		return load;
	}
}