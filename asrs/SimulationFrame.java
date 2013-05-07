package asrs;

import java.awt.*;
import javax.swing.JFrame;

import tspAlgorithm.TSPAlgorithm;

import bppAlgorithm.BPPAlgorithm;

public class SimulationFrame extends JFrame {
	private OrderPickingPanel OPPanel;
	private BinPackingPanel BPPanel;
	
	public SimulationFrame(BPPAlgorithm bpp, TSPAlgorithm tsp){
		setLayout( new FlowLayout() );
		setTitle( "Simulatie" );
		setVisible( true );
		setSize(800,500);
		
		OPPanel = new OrderPickingPanel();
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
