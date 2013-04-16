/**
 * @author Luuk Holleman
 * @date 15 april
 */
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
	
	/**
	 * ctor
	 * 
	 * @author Luuk
	 */
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
	
	/**
	 * Bouwt de ui
	 * 
	 * @author Luuk
	 * 
	 * @return void
	 */
	private void buildUI()
	{
		// hoofdlabel
		add(new JLabel("BPP algoritme"));
		// bpp algoritme button group, zorgt ervoor dat radio button auto worden uitgezet
		ButtonGroup bppBtnGrp = new ButtonGroup();
		
		// loop de bpp algoritmes en plaats de namen in radiobuttons
		for(BPPAlgorithm bppAlgorithm : bppAlgorithms)
		{
			JRadioButton rdBtn = new JRadioButton(bppAlgorithm.getName());
			bppBtnGrp.add(rdBtn);
			add(rdBtn);
		}
		
		// hoofdlabel
		add(new JLabel("TSP algoritme"));
		// tsp algoritme button group, zorgt ervoor dat radio button auto worden uitgezet
		ButtonGroup tspBtnGrp = new ButtonGroup();
		
		// loop de tsp algoritmes en plaats de namen in radiobuttons
		for(TSPAlgorithm tspAlgorithm : tspAlgorithms)
		{
			JRadioButton rdBtn = new JRadioButton(tspAlgorithm.getName());
			tspBtnGrp.add(rdBtn);
			add(rdBtn);
		}
	}
}
