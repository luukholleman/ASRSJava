package taskSimulate;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import order.Location;
import order.Product;

public class OrderPickingTaskSimulation extends JPanel {
	private static final int LINE_INDENT = 12;
	private static final int DOT_INDENT = 7;
	private static final int CELL_SIZE = 25;
	private ArrayList<TravelingSalesmanProblem> problems;
	private int currentProblem = 0;

	public OrderPickingTaskSimulation(
			ArrayList<TravelingSalesmanProblem> problems) {
		super();

		setPreferredSize(new Dimension(400, 200));

		this.problems = problems;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);

		// Hier wordt het magazijn getekend in 20x10
		for (int y = 0; y <LINE_INDENT; y++) {
			for (int x = 0; x <= 20; x++) {
				g.drawRect((x * CELL_SIZE), (y * CELL_SIZE), CELL_SIZE, CELL_SIZE);
			}
		}

		Location lastLocation = null;
		g.setColor(Color.BLUE);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(2));
		
			for (ArrayList<Product> robot : problems.get(currentProblem)
					.getProblem()) {
				for (Product product : robot) {
					Location productLocation = product.getLocation();
					g.fillRect(productLocation.x * CELL_SIZE + DOT_INDENT,
							productLocation.y * CELL_SIZE + DOT_INDENT, 10, 10);
					if (lastLocation != null)
						g2D.drawLine(lastLocation.x * CELL_SIZE + LINE_INDENT,
								lastLocation.y * CELL_SIZE + LINE_INDENT,
								productLocation.x * CELL_SIZE + LINE_INDENT,
								productLocation.y * CELL_SIZE + LINE_INDENT);
						lastLocation = productLocation;
				}
				g.setColor(Color.RED);
				lastLocation = null;
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
