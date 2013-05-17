package taskSimulate;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import order.Location;
import order.Product;

import tspAlgorithm.TSPAlgorithm;
import bppAlgorithm.BPPAlgorithm;
import bppAlgorithm.Bin;

public class SimulationFrame extends JFrame {
	private static final int NUMBER_ROBOTS = 2;

	private JButton lastBtnOrderPicker = new JButton("<-");
	private JButton nextBtnOrderPicker = new JButton("->");
	private JButton lastBtnBinPacker = new JButton("<-");
	private JButton nextBtnBinPacker = new JButton("->");
	ArrayList<Problem> problemsOrderPicking;
	ArrayList<Problem> problemsBinPacking;

	public SimulationFrame(long seed, BPPAlgorithm bpp, TSPAlgorithm tsp) {
		setSize(1200, 700);
		
		// sluit het proces als je op kruisje drukt
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new GridBagLayout());
		
		executeWarehouseTask(seed, tsp);
		
		//Start de task classe
		BinPackingTask binPackingTask = new BinPackingTask(seed);
		binPackingTask.startProcess();
		
		//Arraylist met alle problemen
		ArrayList<BinPackingProblem> problems = ArrayList<BinPackingProblem>();
		
		//Loop door alle problemen heen
		for(int p = 0; p < binPackingTask.getNumberOfProblems(); p++)
		{
			//Haal alle producten uit de task classe
			ArrayList<Product> products = ArrayList<Product>();
			
			for(int i=0;i<binPackingTask.getNumberOfItems(p);i++)
			{
				products.add(new Product(binPackingTask.getItemSize(p,  i), i));
			}
			
			//Maak arraylist met een bin aan
			ArrayList<Bin> bins = new ArrayList<Bin>();
			bins.add(new Bin(binPackingTask.getBinSize(), 0));
			
			BinPackingProblem binPackingProblem = new BinPackingProblem(bins);
		}
		
		
		binPackingTask.finishProcess();
		
	}

	private void buildUI() {
		GridBagConstraints c = new GridBagConstraints();
		// natural height, maximum width
		/**
		 * Linker en rechterkant van het scherm
		 */
		JPanel selectionPanel = new JPanel();

		selectionPanel.setPreferredSize(new Dimension(500, 100));

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;

		selectionPanel.add(lastBtnOrderPicker, c);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.5;
		selectionPanel.add(nextBtnOrderPicker, c);
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.5;
		selectionPanel.add(lastBtnBinPacker, c);
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = 0.5;
		selectionPanel.add(nextBtnBinPacker);

		// plaats de panels
		add(selectionPanel);
		c.gridy = 1;
		c.gridx = 0;
		c.weighty = 5.0;
		add(new OrderPickingTaskSimulation(problemsOrderPicking));

		c.gridy = 1;
		c.gridx = 0;
		c.weighty = 5.0;
		add(new OrderPickingTaskSimulation(problemsOrderPicking));

		c.gridy = 1;
		c.gridx = 0;
		c.weighty = 5.0;
		add(new BinPackingTaskSimulation(problemsBinPacking));
	}

	private void executeWarehouseTask(long seed, TSPAlgorithm tsp) {
		ArrayList<TravelingSalesmanProblem> problems = new ArrayList<TravelingSalesmanProblem>();
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
			for (int r = 0; r < NUMBER_ROBOTS; r++) {
				// Oplossen volgons algoritme
				products.set(r, tsp.calculateRoute(products.get(r), 19, 9));
				for (int i = 0; i < warehouseTask.getNumberOfItems(p); i++) {
					warehouseTask.setOrder(p, products.get(r).get(i).getId(),
							r, i);
				}
			}

			problems.add(new Problem(products));
		}
		warehouseTask.finishProcess();
	}
}
