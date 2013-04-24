package asrs;

import java.awt.*;
import javax.swing.JFrame;

import tspAlgorithm.TSPAlgorithm;

import bppAlgorithm.BPPAlgorithm;

public class SimulatorFrame extends JFrame {
	private OrderPickingSimulatorPanel OPSPanel;
	
	public SimulatorFrame(BPPAlgorithm bpp, TSPAlgorithm tsp){
		//setDefaultCloseOperation( EXIT_ON_CLOSE );
		setLayout( new FlowLayout() );
		setTitle( "Simulation" );
		setVisible( true );
		setSize(600,500);
		
		OPSPanel = new OrderPickingSimulatorPanel(tsp);
		OPSPanel.setPreferredSize(new Dimension(300, 500));
		add(OPSPanel);
		
		revalidate();
	}
}
