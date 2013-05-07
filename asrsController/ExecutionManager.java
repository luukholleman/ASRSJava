package asrsController;

import java.util.ArrayList;

import order.*;
import tspAlgorithm.TSPAlgorithm;
import asrs.*;
import bppAlgorithm.BPPAlgorithm;
import bppAlgorithm.BinManager;

public class ExecutionManager implements Warehouse {
	private Main main;
	private Order order;
	private BinManager binManager;
	private Warehouse warehouse;
	private BinPacking binPacking;
	private TSPAlgorithm tspAlgorithm;
	private BPPAlgorithm bppAlgorithm;
	private Integer width;
	private Integer height;
	private Boolean useDetectedSize;

	public ExecutionManager(Main main, Order order, BinManager binManager,
			Warehouse warehouse, BinPacking binPacking,
			TSPAlgorithm tspAlgorithm, BPPAlgorithm bppAlgorithm,
			Integer width, Integer height, Boolean useDetectedSize) {
		
		ArrayList<Product> products = order.getProducts();
		
		for(Product product : products){
			product.getLocation();
		}
		
		if (getRobots() == 2) {
			/*
			 * Split magazijn
			 * doe de retrieveProduct/pickedUpProduct loop
			 * bringToBinPacker
			 * deliveredProduct
			 * moveToStart
			 * detectedProduct
			 */
		} else {
			/*
			 * doe de retrieveProduct/pickedUpProduct loop
			 * bringToBinPacker
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

	public void pickedUpProduct(Integer robotId) {
		/*
		 * Geeft volgende locatie, tenzij er geen locatie meer is, 
		 * Dan bringToBinPacker() aanroepen
		 */
	}

	public void deliveredProduct(Integer robotId) {
		moveToStart(robotId);
	}

// Interface Methods
	
	public void retrieveProduct(Location location, Integer robotId) {
		//Code die de juiste robot het product laat halen van de locatie
	}

	public void bringToBinPacker() {
		//Code die de robot naar de binpacker stuurt
	}

	public void moveToStart(Integer robotId) {
		//code die de robot terug stuurt naar het begin
	}

	public Integer getRobots() {
		// Code die het aantal robots ophaalt
		return // robotAmount
				null;
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

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	public Boolean getUseDetectedSize() {
		return useDetectedSize;
	}
}
