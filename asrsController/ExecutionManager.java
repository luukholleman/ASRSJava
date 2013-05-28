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
			// Sorteer de producten volgens het meegegeven algoritme
			ArrayList<Product> products = tspAlgorithm.calculateRoute(
					order.getProducts(), warehouse.getNumberOfRobots(), r);

			// init de robot met zijn eigen startlocatie, producten en id
			robots[r] = new WarehouseRobot(warehouse.getStartLocation(r),
					products, r);
		}
	}

	/**
	 * Start de robot, laat de simulatie lopen of laat de arduino bewegen
	 * 
	 * @return void
	 */
	public void start() {
		// we moeten elke robot aanspreken en de id's zijn oplopend, de i is dus
		// het robotid
		int i = 0;

		for (WarehouseRobot wr : robots) {
			// laat de robot het volgende product ophalen
			if (wr.hasNextProduct())
				retrieveNextProduct(i);

			// klaar met deze robot, op naar de volgende
			i++;
		}
	}

	private int getSizeFromColor(byte color) {
		switch (color) {
		case 1:
			System.out.println("Black color detected");
			return 8;
		case 2:
			System.out.println("Blue color detected");
			return 7;
		case 3:
			System.out.println("Green color detected");
			return 9;
		case 4:
			System.out.println("Yellow color detected");
			return 10;
		case 5:
			System.out.println("Red color detected");
			return 3;
		default:
			System.out.println("White color detected");
			return 6;
		}
	}

	/**
	 * Bekijkt de groote van het product als dat moet en geeft volgende locatie,
	 * tenzij er geen locatie meer is, Dan bringToBinPacker() aanroepen.
	 * 
	 * @param robotId
	 */
	public void pickedUpProduct(int robotId, byte color) {

		// Als de gescande kleur moet worden gebruikt als indicatie van de
		// grootte, verander dan lokaal de grootte van het laatst opgepakte
		// product op basis van de kleur.

		if (useDetectedSize && !robots[robotId].productsOnFork.isEmpty()) {
			robots[robotId].productsOnFork.get(
					robots[robotId].productsOnFork.size() - 1).setSize(
					getSizeFromColor(color));
			// De gegeven kleur is door de robot gegeven
		}

		// Als er een product was opgepakt, sla dit product dan lokaal op.
		if (!robots[robotId].getProducts().isEmpty())
			robots[robotId].pickUp(robots[robotId].getProducts().get(0));

		// Als er nog een product op te halen is, en de fork is nog niet vol...
		if (robots[robotId].hasNextProduct()
				&& warehouse.getMaxLoad() > robots[robotId].getProducts()
						.size())

			// Haal het volgende product op
			retrieveNextProduct(robotId);
		else

			// Anders, breng de producten naar de binpacker.
			warehouse.bringToBinPacker(robotId);
	}

	private void retrieveNextProduct(int robotId) {
		// Zoek naar het volgende product...
		Product nextProduct = robots[robotId].getNextProduct();

		// Als er een is, haal deze op.
		warehouse.retrieveProduct(nextProduct.getLocation(), robotId);
	}

	/**
	 * Wordt aangeroepen nadat de producten af geleverd zijn door de robot.
	 * 
	 * @param robotId
	 */
	public void deliveredProduct(int robotId) {

		// Sla eerst alle producten die de robot aflevert op voor de Bin packer,
		// haal de producten daarna van de robot af.
		bppProducts.addAll(robots[robotId].productsOnFork);
		robots[robotId].productsOnFork.clear();

		// Als de robot producten heeft afgeleverd, stuur deze producten dan
		// naar de Bin Packer
		if (!bppProducts.isEmpty()) {
			while (!bppProducts.isEmpty()) {

				// Kijk in welke bin het product het beste past
				Bin bin;
				bin = bppAlgorithm.calculateBin(bppProducts.get(0),
						binManager.bins);

				// Als er een passende bin is gevonden, stuur het nummer daarvan
				// dan door
				if (bin != null) {
					binManager.bins.get(binManager.bins.indexOf(bin)).fill(
							bppProducts.get(0));
					binPacking.packProduct((byte) binManager.bins.indexOf(bin),
							bppProducts.get(0));
					bppProducts.remove(0);
				} else {

					// Als er geen passende bin is, stuur de grootte van de
					// ArrayList toe. Aangezien het terugsturen van een 'null'
					// byte onmogelijk bleek, was dit een passend alternatief.
					binPacking.packProduct((byte) binManager.bins.size(),
							bppProducts.get(0));

					// Haal het product uit de lijst zodat deze niet nog een
					// keer wordt behandelt
					bppProducts.remove(0);
				}
			}

			// Alle producten zijn afgeleverd.

		} else {
			// Als er geen producten zijn afgeleverd, print een bericht en stuur
			// de robot terug.
			System.out
					.println("Robot had no products to deliver, returning to start.");
		}

		if (robots[robotId].hasNextProduct())
			// Als er een volgende product is, haal deze op
			retrieveNextProduct(robotId);

		else
			// Ga terug naar startpositie
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

	public int getLoad() {
		return load;
	}
}