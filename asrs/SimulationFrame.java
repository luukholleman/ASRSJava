package asrs;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;

import tspAlgorithm.TSPAlgorithm;

import bppAlgorithm.BPPAlgorithm;

import order.Product;

public class SimulationFrame extends JFrame {
	private OrderPickingPanel OPPanel;
	private BinPackingPanel BPPanel;
	
	public SimulationFrame(BPPAlgorithm bpp, ArrayList<Product> products){
		setLayout( new FlowLayout() );
		setTitle( "Simulatie" );
		setVisible( true );
		setSize(800,500);
		
		OPPanel = new OrderPickingPanel(products);
		OPPanel.setPreferredSize(new Dimension(350, 500));
		add(OPPanel);
		//Starten van de simulatie
		OPPanel.start();
		
		BPPanel = new BinPackingPanel();
		BPPanel.setPreferredSize(new Dimension(350, 500));
		add(BPPanel);
		//Starten van de simulatie
		BPPanel.start();
		
		
		
		revalidate();
	}
}
