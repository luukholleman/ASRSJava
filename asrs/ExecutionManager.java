package asrs;

import tspAlgorithm.TSPAlgorithm;
import bppAlgorithm.BPPAlgorithm;

public class ExecutionManager {
	private Main main;
	private Order order;
	// private BinManager binManager;
	// private Warehouse warehouse;
	// private BinPacking binPacking;
	private TSPAlgorithm tspAlgorithm;
	private BPPAlgorithm bppAlgorithm;
	private Integer width;
	private Integer height;
	private Boolean useDetectedSize;

	public ExecutionManager(Main main, Order order, //BinManager binManager,
			//Warehouse warehouse, BinPacking binPacking,
			TSPAlgorithm tspAlgorithm, BPPAlgorithm bppAlgorithm,
			Integer width, Integer height, Boolean useDetectedSize) {
		this.main = main;
		//this.binManager = binManager;
		//this.warehouse = warehouse;
		//this.binPacking = binPacking;
		this.tspAlgorithm = tspAlgorithm;
		this.bppAlgorithm = bppAlgorithm;
		this.width = width;
		this.height = height;
		this.useDetectedSize = useDetectedSize;
	}
	
	public Byte detectedProduct(Byte red, Byte green, Byte blue){
		return null;
	}
	
	public void pickedUpProduct(Integer robotId){
		
	}
	
	public void deliveredProduct(Integer robotId){
		
	}
}
