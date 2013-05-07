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
	private Robot[] robots;
	
	public ExecutionManager(Main main, Order order, BinManager binManager,
			Warehouse warehouse, BinPacking binPacking,
			TSPAlgorithm tspAlgorithm, BPPAlgorithm bppAlgorithm,
			int width, int height, Boolean useDetectedSize) {
		
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
		
		robots = new Robot[warehouse.getRobots()];
		for(int r=0; r < warehouse.getRobots(); r++){
			ArrayList<Product> products = new ArrayList<Product>();
			for(Product p : order.getProducts()){
				int cols = width / warehouse.getRobots();
				if(p.getLocation().x >= cols * r && p.getLocation().x < cols * (r + 1)){
					products.add(p);
				}
			}
			products = tspAlgorithm.calculateRoute(products);
			robots[r] = new Robot(warehouse.getStartLocation(r), products);
			
			Product nextProduct = robots[r].getNextProduct();
			if(nextProduct != null)
				warehouse.retrieveProduct(nextProduct.getLocation(), r);
			
			
		}
		
		

			/*
			 * Split magazijn
			 * doe de retrieveProduct/pickedUpProduct/bringToBinPacker loop
			 * deliveredProduct
			 * moveToStart
			 * detectedProduct
			 */

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
		Product nextProduct = robots[r].getNextProduct();
		if(nextProduct != null)
			warehouse.retrieveProduct(nextProduct.getLocation(), r);
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