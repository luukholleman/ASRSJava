package taskSimulate;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import bppAlgorithm.Bin;

public class BinPackingTaskSimulation extends JPanel {
	private static final int BIN_STRING_INDENT = 10;
	private static final int BIN_LENGTH = 300;
	private static final int BIN_WIDTH = 50;
	/**
	 * Een ArrayList met daarin de problemen die moeten worden getekent
	 */
	private ArrayList<BinPackingProblem> problems;
	/**
	 * Een int van het huidige probleem dat wordt getekent
	 */
	private int currentProblem = 0;

	public BinPackingTaskSimulation(ArrayList<BinPackingProblem> problems) {
		super();

		this.problems = problems;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		/*
		 * Teken alle bins en hun inhoudHet nummer van de huidige bin wordt
		 * bijgehouden
		 */
		int count = 0;
		for (Bin bin : problems.get(currentProblem).getBins()) {
			/*
			 * De inhoud van de bin wordt hier getekend door te kijken welk deel
			 * vol zit en dat vanaf de onderkant af te trekken. Zo wordt het van
			 * onder naar boven opgevuld. (Hulp van Mike)
			 */
			g.setColor(Color.green);
			float floaty = (float) BIN_LENGTH - BIN_LENGTH
					* ((float) bin.getFilled() / bin.getSize());
			int y = (int) floaty;
			g.fillRect((count * BIN_WIDTH) + 1, y, BIN_WIDTH - 2, BIN_LENGTH
					- y);
			// Aan de bovenkant van de volheid wordt een streepje getekent
			g.setColor(Color.black);
			g.drawLine((count * BIN_WIDTH), y, (count * BIN_WIDTH) + BIN_WIDTH
					- 3, y);
			/*
			 * De bin is een lang blok dat altijd even groot is. In het midden
			 * ervan staat hoe vol hij is en zijn grootte.
			 */
			g.drawRect((count * BIN_WIDTH), 0, BIN_WIDTH - 2, BIN_LENGTH);
			g.drawString(bin.getFilled() + "/" + bin.getSize(),
					(count * BIN_WIDTH) + BIN_STRING_INDENT, BIN_LENGTH / 2);

			count++;
		}
		// Onderaan de bins staat het nummer van het huidige probleem.
		g.drawString(Integer.toString(currentProblem), BIN_WIDTH,
				BIN_LENGTH + 10);
	}

	/**
	 * Ga naar het volgende probleem als deze er is en teken het opnieuw
	 * 
	 * @author Bas
	 */
	public void nextProblem() {
		if (currentProblem < problems.size() - 1)
			currentProblem++;
		repaint();
	}

	/**
	 * Ga naar het vorige probleem als deze er is en teken het opnieuw
	 * 
	 * @author Bas
	 */
	public void previousProblem() {
		if (currentProblem > 0)
			currentProblem--;
		repaint();
	}
}
