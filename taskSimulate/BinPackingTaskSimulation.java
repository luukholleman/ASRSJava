package taskSimulate;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import bppAlgorithm.Bin;

public class BinPackingTaskSimulation extends JPanel {
	private ArrayList<Bin> bins;
	private ArrayList<BinPackingAnswer> answers;

	public BinPackingTaskSimulation(ArrayList<Bin> bins,
			ArrayList<BinPackingAnswer> answers) {
		super();
		setSize(600, 300);

		this.bins = bins;
		this.answers = answers;
		resolveBins();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);

		// Teken de mee gegeven bins
		int count = 0;
		for (Bin bin : bins) {
			g.drawRect((count * 30), 0, 30, 300);
			g.drawString(bin.getFilled() + "/" + bin.getSize(),
					15 + (count * 30), 150);
			count++;
		}
	}

	private void resolveBins() {
		for (BinPackingAnswer answer : answers) {
			if (bins.contains(answer.getBin()))
				bins.get(bins.indexOf(answer.getBin())).fill(
						answer.getProduct());
		}
	}
}
