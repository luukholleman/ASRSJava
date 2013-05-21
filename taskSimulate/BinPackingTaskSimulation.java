package taskSimulate;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import bppAlgorithm.Bin;

public class BinPackingTaskSimulation extends JPanel {
	private ArrayList<BinPackingProblem> problems;
	private int currentProblem = 0;

	public BinPackingTaskSimulation(ArrayList<BinPackingProblem> problems) {
		super();

		this.problems = problems;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Teken de mee gegeven bins
		int count = 0;
		for (Bin bin : problems.get(currentProblem).getBins()) {
			g.setColor(Color.green);
			g.fillRect((count * 40) + 1, 1, 37,
					300 / bin.getSize() * bin.getFilled());
			g.setColor(Color.black);
			g.drawRect((count * 40), 0, 38, 300);
			g.drawString(bin.getFilled() + "/" + bin.getSize(),
					5 + (count * 40), 150);

			count++;
		}
	}

	public void nextProblem() {
		if(currentProblem < problems.size()-1)
			currentProblem++;
		repaint();
	}

	public void previousProblem() {
		if(currentProblem > 0)
			currentProblem--;
		repaint();
	}
}
