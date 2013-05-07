package asrsController;

import java.util.ArrayList;

import order.*;
import tspAlgorithm.TSPAlgorithm;
import asrs.*;
import bppAlgorithm.BPPAlgorithm;
import bppAlgorithm.BinManager;

public class ExecutionManager{
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
	
	private ArrayList<Location> locations = new ArrayList<Location>();

	public ExecutionManager(Main main, Order order, BinManager binManager,
			Warehouse warehouse, BinPacking binPacking,
			TSPAlgorithm tspAlgorithm, BPPAlgorithm bppAlgorithm,
			int width, int height, Boolean useDetectedSize) {
		
		ArrayList<Product> products = order.getProducts();
		products = tspAlgorithm.calculateRoute(products);
		
		for(Product product : products)
			locations.add(product.getLocation());
				
		if (warehouse.getRobots() == 2) {
			/*
			 * Split magazijn
			 * doe de retrieveProduct/pickedUpProduct/bringToBinPacker loop
			 * deliveredProduct
			 * moveToStart
			 * detectedProduct
			 */
		} else {
			/*
			 * doe de retrieveProduct/pickedUpProduct/bringToBinPacker loop
			 * deliveredProduct
			 * moveToStart
			 * detectedProduct
			 */
		}

	}

	public Byte detectedProduct(Byte red, Byte green, Byte blue) {
		/*
		 * Oproepen door bppArduino, kleur naar grootte omzetten. byte wordt
		 * binnummer-1 (0 is bin 1, 1 is bin 2, etc.)
		 */
		return null;
	}

	public Location pickedUpProduct(int robotId) {
		/*
		 * Geeft volgende locatie, tenzij er geen locatie meer is, 
		 * Dan bringToBinPacker() aanroepen
		 */
		return null;
	}

	public void deliveredProduct(Integer robotId) {
		warehouse.moveToStart(robotId);
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
	
	public int getLoad(){
		return load;
	}
}