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
		setSize(600, 300);

		this.problems = problems;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);

		// Teken de mee gegeven bins
		int count = 0;
		for (Bin bin : problems.get(0).getBins()) {
			g.drawRect((count * 30), 0, 30, 300);
			g.drawString(bin.getFilled() + "/" + bin.getSize(),
					15 + (count * 30), 150);
			count++;
		}
	}
	
	public void nextProblem() {
		currentProblem++;
		repaint();
	}

	public void previousProblem() {
		currentProblem--;
		repaint();
	}
}
