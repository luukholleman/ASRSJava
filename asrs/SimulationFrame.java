package asrs;

import java.awt.*;
import javax.swing.JFrame;

import tspAlgorithm.TSPAlgorithm;

import bppAlgorithm.BPPAlgorithm;

public class SimulationFrame extends JFrame {
	private OrderPickingPanel OPPanel;
	private BinPackingPanel BPPanel;
//	private BinPackingPanel BPPanel;
	
	public SimulationFrame(BPPAlgorithm bpp, TSPAlgorithm tsp){
		//setDefaultCloseOperation( EXIT_ON_CLOSE );
		setLayout( new FlowLayout() );
		setTitle( "Simulation" );
		setVisible( true );
		setSize(800,500);
		
		OPPanel = new OrderPickingPanel(tsp);
		OPPanel.setPreferredSize(new Dimension(350, 500));
		add(OPPanel);
		OPPanel.start();
		
		BPPanel = new BinPackingPanel(bpp);
		BPPanel.setPreferredSize(new Dimension(350, 500));
		add(BPPanel);
		BPPanel.start();
		
		
		
		revalidate();
	}
}
