package asrs;

import tspAlgorithm.TSPAlgorithm;
import bppAlgorithm.BPPAlgorithm;

public class ExecutionManager {
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
		if(warehouse.getRobots() == 2){
			/**
			 * splits het magazijn
			 * gebruik het algoritme op beide delen.
			 * commandeer bot/sim
			 */
		} else {
			/**
			 * Pas het algoritme toe op het magazijn
			 * commandeer bot/sim
			 */
		}
		
	}
	
	public Byte detectedProduct(Byte red, Byte green, Byte blue){
		/**
		 * Oproepen door bppArduino, kleur naar grootte omzetten.
		 * byte wordt binnummer-1 (0 is bin 1, 1 is bin 2, etc.)
		 */
		return null;
	}
	
	public void pickedUpProduct(Integer robotId){
		/**
		 * Geeft volgende locatie, tenzij er geen locatie meer is, dan naar binpacker gaan
		 */
		if(warehouse.getRobots() == 2){
			/**
			 * gebruik de locatie
			 * Kijk in welk deel van het gesplitste magazijn het ligt
			 * geef door naar de bot die er over gaat
			 * geen producten meer, naar binpacker
			 */
		} else {
			/**
			 * gebruik de locatie
			 * geef door naar de bot
			 * geen producten meer, naar binpacker
			 */
		}
	}
	
	public void deliveredProduct(Integer robotId){
		warehouse.moveToStart();
	}
	
	
	
	/**
	 * Alle getters
	 */
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
