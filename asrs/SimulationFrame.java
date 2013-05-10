package asrs;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;

import tspAlgorithm.TSPAlgorithm;

import asrsController.ExecutionManager;
import bppAlgorithm.BPPAlgorithm;

import order.Product;

public class SimulationFrame extends JFrame {
	private OrderPickingPanel OPPanel;
	private BinPackingPanel BPPanel;
	
	public SimulationFrame(BPPAlgorithm bpp, TSPAlgorithm tsp, ExecutionManager EM){
		setLayout( new FlowLayout() );
		setTitle( "Simulatie" );
		setVisible( true );
		setSize(800,500);
		
		
		BPPanel = new BinPackingPanel(EM);
		BPPanel.setPreferredSize(new Dimension(350, 500));
		add(BPPanel);
		//Starten van de simulatie
		BPPanel.start();
		
		
		OPPanel = new OrderPickingPanel(EM, BPPanel);
		OPPanel.setPreferredSize(new Dimension(350, 500));
		add(OPPanel);
		//Starten van de simulatie
		OPPanel.start();

		
		
		revalidate();
	}
}
