package taskSimulate;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import order.Location;
import order.Product;

import tspAlgorithm.TSPAlgorithm;
import bppAlgorithm.BPPAlgorithm;
import bppAlgorithm.Bin;

public class TaskSimulationFrame extends JFrame implements ActionListener {

	private static final int NUMBER_ROBOTS = 2;
	/**
	 * De knop voor het vorige Warehouse probleem
	 */
	private JButton previousBtnOrderPicker = new JButton("<-");
	/**
	 * De knop voor het volgende Warehouse probleem
	 */
	private JButton nextBtnOrderPicker = new JButton("->");
	/**
	 * De knop voor het vorige Bin Packer probleem
	 */
	private JButton previousBtnBinPacker = new JButton("<-");
	/**
	 * De knop voor het volgende Bin Packer probleem
	 */
	private JButton nextBtnBinPacker = new JButton("->");
	/**
	 * Het panel voor het warehouse
	 */
	private OrderPickingTaskSimulation orderPickingPanel;
	/**
	 * Het panel voor de Bin Packer
	 */
	private BinPackingTaskSimulation binPackingPanel;
	/**
	 * De ArrayList met alle warehouse problemen
	 */
	private ArrayList<TravelingSalesmanProblem> problemsOrderPicking;
	/**
	 * De ArrayList met alle Bin Packing problemen
	 */
	private ArrayList<BinPackingProblem> problems;

	public TaskSimulationFrame(long seed, BPPAlgorithm bppAlgorithm,
			TSPAlgorithm tspAlgorithm) {
		setSize(1000, 500);

		/*
		 * GridBag wordt gebruikt omdat de knoppen bovenaan het scherm kleiner
		 * zijn de de panels.
		 */
		setLayout(new GridBagLayout());

		// Hier wordt de gehele Warehouse Task uitgevoert.
		executeWarehouseTask(seed, tspAlgorithm);

		// Hier wordt de Bin Packing Task uitgevoert.
		executeBinPackingTask(seed, bppAlgorithm);

		// En hier wordt alles getekend.
		executeBinPackingTask(seed, bppAlgorithm);
		buildUI();
	}

	/**
	 * Voert de BinPacking Task uit.
	 * 
	 * @param seed
	 * @param bppAlgorithm
	 * 
	 * @author Bas, Tim
	 */
	private void executeBinPackingTask(long seed, BPPAlgorithm bppAlgorithm) {
		// Start de task classe
		BinPackingTask binPackingTask = new BinPackingTask(seed);
		binPackingTask.startProcess();

		// Arraylist met alle problemen
		problems = new ArrayList<BinPackingProblem>();
		// Loop door alle problemen heen
		for (int p = 0; p < binPackingTask.getNumberOfProblems(); p++) {
			// Maak arraylist met een bin aan
			ArrayList<Bin> bins = new ArrayList<Bin>();
			bins.add(new Bin(binPackingTask.getBinSize(), 0));

			// Haal alle producten uit de task classe
			for (int i = 0; i < binPackingTask.getNumberOfItems(p); i++) {
				// Maak het product aan zoals in de task staat
				Product product = new Product(binPackingTask.getItemSize(p, i),
						i);

				// Bereken in welke bin dit product moet gaan
				Bin bin = bppAlgorithm.calculateBin(product, bins);
				if (bin == null) {
					bin = new Bin(binPackingTask.getBinSize(), 0);
					bins.add(bin);
				}

				// Vul deze bin op en geef deze vulling door aan de task
				bins.get(bins.indexOf(bin)).fill(product);
				binPackingTask.setBin(p, i, bins.indexOf(bin));
			}

			problems.add(new BinPackingProblem(bins));
		}

		binPackingTask.finishProcess();
	}

	/**
	 * Voert de warehouse task uit
	 * 
	 * @param seed
	 * @param tsp
	 * 
	 * @author Bas, Tim
	 */
	private void executeWarehouseTask(long seed, TSPAlgorithm tsp) {

		problemsOrderPicking = new ArrayList<TravelingSalesmanProblem>();
		WarehouseTask warehouseTask = new WarehouseTask(seed);
		// Start timer
		warehouseTask.startProcess();

		// Door alle problemen heen lopen
		for (int p = 0; p < warehouseTask.getNumberOfProblems(); p++) {

			// Lijst van de producten per robot
			ArrayList<ArrayList<Product>> problem = new ArrayList<ArrayList<Product>>();

			// Lijst van alle producten
			ArrayList<Product> products = new ArrayList<Product>();

			// Initializeren robots lijst
			for (int r = 0; r < NUMBER_ROBOTS; r++)
				problem.add(new ArrayList<Product>());

			// Doorlopen van alle items
			for (int i = 0; i < warehouseTask.getNumberOfItems(p); i++) {

				// Sla de locatie van de items op in een product
				products.add(new Product(new Location(warehouseTask
						.getCoordHorDigit(p, i), warehouseTask
						.getCoordVertDigit(p, i)), i));

			}

			// Bereken nu de volgorde volgends het algoritme
			for (int r = 0; r < NUMBER_ROBOTS; r++) {

				// Oplossen volgens algoritme
				problem.set(r, tsp.calculateRoute(products, NUMBER_ROBOTS, r));

				// Geef alles van dit probleem door aan task classe
				for (int i = 0; i < problem.get(r).size(); i++)
					warehouseTask.setOrder(p, problem.get(r).get(i).getId(), r,
							i);
			}

			// Voeg dit probleem toe aan problemenlijst
			problemsOrderPicking.add(new TravelingSalesmanProblem(problem));
		}
		warehouseTask.finishProcess();
	}

	/**
	 * Maakt alle UI elementen aan en geeft de uitkomsten van de tasks mee aan
	 * panels
	 * 
	 * @author Bas
	 */
	private void buildUI() {
		GridBagConstraints c = new GridBagConstraints();
		// natural height, maximum width
		/**
		 * Linker en rechterkant van het scherm
		 */

		// Opvullen selection Panel
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;

		add(previousBtnOrderPicker, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		add(nextBtnOrderPicker, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		add(previousBtnBinPacker, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = 0.25;
		c.weighty = 0.5;
		add(nextBtnBinPacker, c);

		// plaats de panels
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 2;
		c.weighty = 0.5;
		c.weightx = 0.5;
		c.ipady = 400;
		add(orderPickingPanel = new OrderPickingTaskSimulation(
				problemsOrderPicking), c);

		c.gridy = 1;
		c.gridx = 2;
		c.gridwidth = 2;
		c.weighty = 0.5;
		c.weightx = 0.5;
		c.ipady = 400;
		add(binPackingPanel = new BinPackingTaskSimulation(problems), c);

		nextBtnOrderPicker.addActionListener(this);
		previousBtnOrderPicker.addActionListener(this);
		nextBtnBinPacker.addActionListener(this);
		previousBtnBinPacker.addActionListener(this);

		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		/*
		 * Als er op een knop wordt gedrukt, wordt er altijd het volgende of
		 * vorige probleem getoont.
		 */
		if (event.getSource() == nextBtnOrderPicker)
			orderPickingPanel.nextProblem();
		if (event.getSource() == previousBtnOrderPicker)
			orderPickingPanel.previousProblem();
		if (event.getSource() == nextBtnBinPacker)
			binPackingPanel.nextProblem();
		if (event.getSource() == previousBtnBinPacker)
			binPackingPanel.previousProblem();
	}

	private class TSPThread implements Runnable {

		private TravelingSalesmanProblem travelingSalesmanProblem = null;

		private WarehouseTask warehouseTask;
		private int problemId;
		private TSPAlgorithm tspAlgorithm;

		private Boolean isDone = false;
		
		public TSPThread(WarehouseTask warehouseTask, int problemId, TSPAlgorithm tspAlgorithm) {
			this.warehouseTask = warehouseTask;
			this.problemId = problemId;
			this.tspAlgorithm = tspAlgorithm;
		}

		//@Override
		public void run() {
			//Debug
//			System.out.println("TSP Thread for problem " + problemId + " started");
			
			 // Lijst van de producten per robot
			 ArrayList<ArrayList<Product>> problem = new
			 ArrayList<ArrayList<Product>>();
			
			 // Lijst van alle producten
			 ArrayList<Product> products = new ArrayList<Product>();
			
			 // Initializeren robots lijst
			 for (int r = 0; r < NUMBER_ROBOTS; r++)
			 problem.add(new ArrayList<Product>());
			
			 // Doorlopen van alle items
			 for (int i = 0; i < warehouseTask.getNumberOfItems(problemId); i++) {
			
			 // Sla de locatie van de items op in een product
			 products.add(new Product(new Location(warehouseTask
			 .getCoordHorDigit(problemId, i), warehouseTask
			 .getCoordVertDigit(problemId, i)), i));
			
			 }
			
			 // DEBUG
			 System.out.println("Probleem: " + problemId + ", producten: " + products.size());
			 // System.out.println("===========================");
			 // System.out.println("PRINTING ALL PRODUCTS:");
			 // for(Product pr : products)
			 // System.out.println(pr);
			 // System.out.println("===========================");
			 // END DEBUG
			
			 // //Bereken nu de volgorde volgends het algoritme
			 for (int r = 0; r < NUMBER_ROBOTS; r++) {
			
			 // Oplossen volgens algoritme
			 problem.set(r, tspAlgorithm.calculateRoute(products, NUMBER_ROBOTS, r));
			
			 //Geef alles van dit probleem door aan task classe
			 for (int i = 0; i < problem.get(r).size(); i++)
			 warehouseTask.setOrder(problemId, problem.get(r).get(i).getId(), r, i);
			
			 }
			
			 travelingSalesmanProblem = new TravelingSalesmanProblem(problem);
			
			//We zijn klaar
			isDone=true;
			
//			System.out.println("TSP Thread for problem " + problemId + " ended");
		}

		public TravelingSalesmanProblem getResult() {
			return travelingSalesmanProblem;
		}
		
		public Boolean isDone()
		{
			return isDone;
		}
	}
}
