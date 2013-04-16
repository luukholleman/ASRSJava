import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import bppAlgorithm.AlmostWorstFit;
import bppAlgorithm.BPPAlgorithm;
import bppAlgorithm.BestFit;
import bppAlgorithm.FirstFit;
import bppAlgorithm.FullBin;
import bppAlgorithm.NextFit;
import bppAlgorithm.WorstFit;



public class ExecutionPanel extends JPanel {
	// bpp algoritmes
	private ArrayList<BPPAlgorithm> bppAlgorithms = new ArrayList<BPPAlgorithm>();
	
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
		
		buildUI();
	}
	
	private void buildUI()
	{
		// hoofdlabel
		add(new JLabel("BPP algoritme"));
		
		// loop de bpp algoritmes en plaats de namen in radiobuttons
		for(BPPAlgorithm bppAlgorithm : bppAlgorithms)
			add(new JRadioButton(bppAlgorithm.getName()));
	}
}
