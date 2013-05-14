package asrs;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import order.Order;
import order.Product;

import tspAlgorithm.TSPAlgorithm;
import asrsController.ExecutionManager;
import bppAlgorithm.BPPAlgorithm;
import bppAlgorithm.Bin;
import bppAlgorithm.BinManager;

import listener.XMLUploadedListener;
import listener.ExecuteButtonPressedListener;;


/**
 * 
 */

/**
 * @author Luuk
 *
 */
public class Main extends JFrame implements XMLUploadedListener, ExecuteButtonPressedListener {
	/**
	 * Panels voor elke fieldset
	 */
	private XMLPanel xmlPanel = new XMLPanel();
	private ExecutionPanel executionPanel = new ExecutionPanel();
	private CustomerPanel customerPanel = new CustomerPanel();
	private OrderPanel orderPanel = new OrderPanel();
	
	private Order order;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try
		{
			DBHandler.connect();
		}
		catch(DatabaseConnectionFailedException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		// zet de look and feel naar windows of osx
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			System.out.println("Unable to load look and feel");
		}
		
		// creeer het scherm
		JFrame main = new Main();
		// TODO Auto-generated method stub
	}
	
	public Main()
	{
		setSize(1200, 700);
		
		// sluit het proces als je op kruisje drukt
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/**
		 * Het scherm is op te delen in 2 kolommen
		 * De flowlayout zorgt voor de kolommen en de panels 
		 * zorgen dat we meerdere componenten in 1 kant kunnen stoppen
		 */
		setLayout(new BorderLayout());
		
		buildUI();
		bindListeners();
		
		// als laatste, maak hem zichtbaar
		setVisible(true);
	}
	
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

		leftPanel.add(xmlPanel);
		leftPanel.add(executionPanel);
		leftPanel.add(customerPanel);
		rightPanel.add(orderPanel);
	
		// plaats de panels
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
	}
	
	private void bindListeners()
	{
		// voeg listeners toe
		xmlPanel.addXMLUploadListener(this);
		executionPanel.addExecutionListener(this);
	}

	@Override
	public void xmlUploaded(String xmlFileLocation) {
		try {
			order = XMLLoader.readOrder(xmlFileLocation);
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		customerPanel.setCustomerId(order.getCustomer().getId());
		customerPanel.setCustomerName(order.getCustomer().getName());
		customerPanel.setDate(order.getDate());
		customerPanel.setTotalPrice(order.getTotalPrice());
		
		orderPanel.setOrder(order);
	}

	@Override
	public void simulatePressed(BPPAlgorithm bpp, TSPAlgorithm tsp) {
		if(order == null) {
			JOptionPane.showMessageDialog(this, "Selecteer eerst een XML bestand");
			return;
		}
		
		BinManager binMan = new BinManager();
		binMan.addBin(new Bin(10,0));
		binMan.addBin(new Bin(20,0));
		binMan.addBin(new Bin(30,0));
		BinPackingPanel bpPanel = new BinPackingPanel();
		OrderPickingPanel opPanel = new OrderPickingPanel(bpPanel);
		ExecutionManager eM = new ExecutionManager(this, order, binMan, opPanel, bpPanel, tsp, bpp, 10, 20, false);
		bpPanel.setEM(eM);
		opPanel.setEM(eM);
		SimulationFrame frame = new SimulationFrame(bpPanel, opPanel);
		frame.setVisible(true);
	}

	@Override
	public void executePressed(BPPAlgorithm bpp, TSPAlgorithm tsp,	
			String com1, String com2) {
		if(order == null) {
			JOptionPane.showMessageDialog(this, "Selecteer eerst een XML bestand");
			return;
		}
		
		throw new UnsupportedOperationException();
		// TODO Auto-generated method stub
		
	}

	public void productStatusUpdated(Product product)
	{
		orderPanel.updateStatus(product, product.getStatus());
	}
}
