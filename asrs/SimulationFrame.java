package asrs;

import java.awt.*;
import javax.swing.JFrame;

import tspAlgorithm.TSPAlgorithm;

import bppAlgorithm.BPPAlgorithm;

public class SimulationFrame extends JFrame {
	private OrderPickingPanel OPPanel;
//	private BinPackingPanel BPPanel;
	
	public SimulationFrame(BPPAlgorithm bpp, TSPAlgorithm tsp){
		//setDefaultCloseOperation( EXIT_ON_CLOSE );
		setLayout( new FlowLayout() );
		setTitle( "Simulation" );
		setVisible( true );
		setSize(600,500);
		
		OPPanel = new OrderPickingPanel(tsp);
		OPPanel.setPreferredSize(new Dimension(300, 500));
		add(OPPanel);
		
		revalidate();
	}
}
