import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


import bppAlgorithm.BPPAlgorithm;
import bppAlgorithm.AlmostWorstFit;
import bppAlgorithm.BestFit;
import bppAlgorithm.FirstFit;
import bppAlgorithm.FullBin;
import bppAlgorithm.NextFit;
import bppAlgorithm.WorstFit;

import tspAlgorithm.BruteForce;
import tspAlgorithm.Greedy;
import tspAlgorithm.TSPAlgorithm;
import tspAlgorithm.TwoOpt;



public class ExecutionPanel extends JPanel {
	// bpp algoritmes
	private ArrayList<BPPAlgorithm> bppAlgorithms = new ArrayList<BPPAlgorithm>();
	private ArrayList<TSPAlgorithm> tspAlgorithms = new ArrayList<TSPAlgorithm>();
	
	public ExecutionPanel()
	{
		setBorder(BorderFactory.createTitledBorder("Uitvoeren"));
		
		setPreferredSize(new Dimension(500, 200));
		
		bppAlgorithms.add(new FirstFit());
		bppAlgorithms.add(new BestFit());
		bppAlgorithms.add(new FullBin());
		bppAlgorithms.add(new NextFit());
		bppAlgorithms.add(new WorstFit());
		bppAlgorithms.add(new AlmostWorstFit());
		
		tspAlgorithms.add(new BruteForce());
		tspAlgorithms.add(new Greedy());
		tspAlgorithms.add(new TwoOpt());
		
		buildUI();
	}
	
	private void buildUI()
	{
		// hoofdlabel
		add(new JLabel("BPP algoritme"));
		
		// loop de bpp algoritmes en plaats de namen in radiobuttons
		for(BPPAlgorithm bppAlgorithm : bppAlgorithms)
			add(new JRadioButton(bppAlgorithm.getName()));
		
		// hoofdlabel
		add(new JLabel("TSP algoritme"));
		
		// loop de tsp algoritmes en plaats de namen in radiobuttons
		for(TSPAlgorithm tspAlgorithm : tspAlgorithms)
			add(new JRadioButton(tspAlgorithm.getName()));
	}
}
