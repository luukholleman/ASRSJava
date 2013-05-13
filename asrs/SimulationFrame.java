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
	
	public SimulationFrame(BinPackingPanel bpPanel, OrderPickingPanel opPanel){
		setLayout( new FlowLayout() );
		setTitle( "Simulatie" );
		setVisible( true );
		setSize(800,500);

		bpPanel.setPreferredSize(new Dimension(350, 500));
		add(bpPanel);
		//Starten van de simulatie
		bpPanel.start();

		opPanel.setPreferredSize(new Dimension(350, 500));
		add(opPanel);
		//Starten van de simulatie
		opPanel.start();

		revalidate();
	}
}
