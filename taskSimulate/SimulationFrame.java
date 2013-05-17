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

public class SimulationFrame extends JFrame {
	private static final int NUMBER_ROBOTS = 2;
	
	private JButton lastBtnOrderPicker = new JButton("<-");
	private JButton nextBtnOrderPicker = new JButton("->");
	private JButton lastBtnBinPacker = new JButton("<-");
	private JButton nextBtnBinPacker = new JButton("->");

	public SimulationFrame(long seed, BPPAlgorithm bpp, TSPAlgorithm tsp) {
		setSize(1200, 700);
		
		// sluit het proces als je op kruisje drukt
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new GridBagLayout());
		
		executeWarehouseTask(seed, tsp);
		
		//Begin BinPacking algoritmes
		
		BinPackingTask binPackingTask = new BinPackingTask(seed);
		binPackingTask.startProcess();
		
	}
	
	private void buildUI()
	{
		GridBagConstraints c = new GridBagConstraints();
        //natural height, maximum width
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
		/**
		 * Linker en rechterkant van het scherm
		 */
		JPanel leftSelectionPanel = new JPanel();
		JPanel rightSelectionPanel = new JPanel();
		
		/** 
		 * zet de leftpanel op een breedte
		 * de rechter heeft dit niet nodig omdat daar maar 1 panel in zit
		 */
		leftSelectionPanel.setPreferredSize(new Dimension(500, 100));
		
		 c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
		
		leftSelectionPanel.add(lastBtnOrderPicker, c);
		leftSelectionPanel.add(nextBtnOrderPicker);
		rightSelectionPanel.add(lastBtnBinPacker);
		rightSelectionPanel.add(nextBtnBinPacker);
	
		// plaats de panels
		add(leftSelectionPanel, GridBagLayout.);
		add(rightSelectionPanel, BorderLayout.CENTER);
	}
	

	private void executeWarehouseTask(long seed, TSPAlgorithm tsp) {
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
	}
}
