package taskSimulate;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import bppAlgorithm.Bin;

public class BinPackingTaskSimulation extends JPanel {
	private static final int BIN_WIDTH = 50;
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
			float floaty = (float) 300 -  300 * ( (float) bin.getFilled() / bin.getSize() );
			int y = (int) floaty;
			g.fillRect((count * BIN_WIDTH) + 1, y, 47,
					300 - y);
			g.setColor(Color.black);
			g.drawRect((count * BIN_WIDTH), 0, 48, 300);
			g.drawString(bin.getFilled() + "/" + bin.getSize(),
					5 + (count * BIN_WIDTH), 150);

			count++;
		}
		g.drawString(Integer.toString(currentProblem), BIN_WIDTH, 310);
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
