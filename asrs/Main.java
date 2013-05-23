package asrs;
import gnu.io.CommPortIdentifier;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import listener.ExecuteButtonPressedListener;
import listener.XMLUploadedListener;
import order.Order;
import order.Product;
import taskSimulate.TaskSimulationFrame;
import tspAlgorithm.TSPAlgorithm;
import asrsController.BinPackingArduino;
import asrsController.ExecutionManager;
import asrsController.WarehouseArduino;
import bppAlgorithm.BPPAlgorithm;
import bppAlgorithm.Bin;
import bppAlgorithm.BinManager;

/**
 * De main class is de core class in deze applicatie, hij start de init van de gui en koppelt de verschillende packages aan elkaar
 * 
 * @author Luuk
 */
public class Main extends JFrame implements XMLUploadedListener, ExecuteButtonPressedListener {
	/**
	 * Panel voor xml uploaden
	 */
	private XMLPanel xmlPanel = new XMLPanel();
	/**
	 * Panel voor uitvoeren
	 */
	private ExecutionPanel executionPanel = new ExecutionPanel();
	/**
	 * Panel voor klantinformatie
	 */
	private CustomerPanel customerPanel = new CustomerPanel();
	/**
	 * Panel voor producten weergeven
	 */
	private OrderPanel orderPanel = new OrderPanel();
	
	/**
	 * de ingelezen order
	 */
	private Order order;

	/**
	 * @param args
	 * 
	 * @return void
	 */
	public static void main(String[] args) {
		// probeer met de database verbinding te maken
		try {
			Database.connect();
		} catch(DatabaseConnectionFailedException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			
			// we kunnen niet verder zonder database, afkappen die zooi
			return;
		}
		
		// zet de look and feel naar windows of osx (of linux)
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// class niet gevonden, laat een melding zien. programma kan wel verder zonder enkel probleem
			System.out.println("Unable to load look and feel");
		}
		
		// start de executie
		new Main();
	}
	
	/**
	 * Ctor
	 */
	public Main()
	{
		// afmeting van het scherm
		setSize(1200, 700);
		
		// titel van applicatie
		setTitle("Auto Dropbox");
		
		// sluit het proces als je op kruisje drukt
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/**
		 * Het scherm is op te delen in 2 kolommen
		 * De flowlayout zorgt voor de kolommen en de panels 
		 * zorgen dat we meerdere componenten in 1 kant kunnen stoppen
		 */
		setLayout(new BorderLayout());
		
		// start de ui
		buildUI();
		
		// bind listeners zodat we acties kunnen tracken
		bindListeners();
		
		// applicatie is klaar, laat hem zien
		setVisible(true);
	}
	
	/**
	 * Zorgt voor het weergeven van de ui
	 * 
	 * @return void
	 */
	private void buildUI()
	{
		/**
		 * Linker en rechterkant van het scherm
		 */
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		
		/** 
		 * zet de leftpanel op een breedte
		 * de rechter heeft dit niet nodig omdat daar maar 1 panel in zit
		 */
		leftPanel.setPreferredSize(new Dimension(500, 700));

		// plaats de panels in het frame, elk panel tekent zijn eigen ui weer
		leftPanel.add(xmlPanel);
		leftPanel.add(executionPanel);
		leftPanel.add(customerPanel);
		rightPanel.add(orderPanel);
	
		// plaats de panels
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Bind de listeners zodat we events kunnen waarnemen
	 * 
	 * @return void
	 */
	private void bindListeners()
	{
		// voeg listeners toe
		xmlPanel.addXMLUploadListener(this);
		executionPanel.addExecutionListener(this);
	}

	/**
	 * Event voor xml upload
	 * 
	 * @param String xmlFileLocation
	 * 
	 * @return void
	 */
	@Override
	public void xmlUploaded(String xmlFileLocation) {
		// probeer de order class te maken
		try {
			order = XMLLoader.readOrder(xmlFileLocation);
		} catch (ProductNotFoundException e) {

			// er bestaat een product niet, laat een melding zien
			JOptionPane.showMessageDialog(null, e.getMessage());
			
			e.printStackTrace();
			
			// applicatie kan niet verder, stoppen zodat de gebruiker een nieuw bestand kan uploaden
			return;
		}
		
		// zet de gegevens in het customer panel
		customerPanel.setCustomerId(order.getCustomer().getId());
		customerPanel.setCustomerName(order.getCustomer().getName());
		customerPanel.setDate(order.getDate());
		customerPanel.setTotalPrice(order.getTotalPrice());
		
		// order doorgeven aan het orderpanel, zodat deze het in de tabel kan weergeven
		orderPanel.setOrder(order);
	}

	/**
	 * Event voor het starten van de simulatie
	 * 
	 * @param BPPAlgorithm bpp
	 * @param TSPAlgorithm tsp
	 * 
	 * @return void
	 */
	@Override
	public void simulatePressed(BPPAlgorithm bpp, TSPAlgorithm tsp) {
		// controleer eerst of er al een order bestaat, zo nee, stop ermee
		if(order == null) {
			JOptionPane.showMessageDialog(this, "Selecteer eerst een XML bestand");
			return;
		}
		
		// maak een binmanager aan
		BinManager binManager = new BinManager();
		
		// voeg een paar bins toe
		binManager.addBin(new Bin(10,0));
		binManager.addBin(new Bin(20,0));
		binManager.addBin(new Bin(30,0));
		
		// maak de panels aan
		BinPackingPanel bpPanel = new BinPackingPanel();
		OrderPickingPanel opPanel = new OrderPickingPanel(bpPanel);
		
		// maak de executionmanager aan me de net aangemaakte gegevens
		ExecutionManager executionManager = new ExecutionManager(this, order, binManager, opPanel, bpPanel, tsp, bpp, 10, 20, false);
		
		// geef de em door aan de panels zodat ze de gegevens kunnen uitlezen
		bpPanel.setEM(executionManager);
		opPanel.setEM(executionManager);
		
		// maak een frame aan met de panels die we net gemaakt hebben
		SimulationFrame frame = new SimulationFrame(bpPanel, opPanel);
		frame.setVisible(true);
	}

	/**
	 * @TODO
	 */
	@Override
	public void executePressed(BPPAlgorithm bpp, TSPAlgorithm tsp,	
			CommPortIdentifier comBpp, CommPortIdentifier comTsp) {
		if(order == null) {
			JOptionPane.showMessageDialog(this, "Selecteer eerst een XML bestand");
			return;
		}
		
		// maak een binmanager aan
		BinManager binManager = new BinManager();
		
		// voeg een paar bins toe
		binManager.addBin(new Bin(20,0));
		binManager.addBin(new Bin(20,0));
		binManager.addBin(new Bin(20,0));
		
		// maak de Arduino klasses aan aan
		BinPackingArduino binPackingArduino = new BinPackingArduino(comBpp);
		WarehouseArduino warehouseArduino = new WarehouseArduino(comTsp);
		
		// maak de executionmanager aan me de net aangemaakte gegevens
		ExecutionManager executionManager = new ExecutionManager(this, order, binManager, warehouseArduino, binPackingArduino, tsp, bpp, 10, 20, false);
		
		warehouseArduino.setExecutionManager(executionManager);
		
		executionManager.start();
		
//		warehouseArduino.start();
		
		
		System.out.println("test");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void simulateTaskPressed(BPPAlgorithm bpp, TSPAlgorithm tsp, long seed) {
		System.out.println(seed);
		TaskSimulationFrame taskFrame = new TaskSimulationFrame(seed, bpp, tsp);
		taskFrame.setVisible(true);
	}

	/**
	 * Update de status van een product
	 * 
	 * @param product
	 * 
	 * @return void
	 */
	public void productStatusUpdated(Product product)
	{
		orderPanel.updateStatus(product, product.getStatus());
	}
}
