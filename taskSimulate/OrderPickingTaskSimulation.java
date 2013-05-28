package taskSimulate;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import order.Location;
import order.Product;

public class OrderPickingTaskSimulation extends JPanel {
	private static final int LINE_WIDTH = 2;
	private static final int STARTING_X1 = -1;
	private static final int PRODUCT_SIZE = 10;
	private static final int WAREHOUSE_X = 19;
	public static final int WAREHOUSE_Y = 9;
	private static final int LINE_INDENT = 12;
	private static final int DOT_INDENT = 7;
	private static final int CELL_SIZE = 25;
	private ArrayList<TravelingSalesmanProblem> problems;
	private int currentProblem = 0;

	public OrderPickingTaskSimulation(
			ArrayList<TravelingSalesmanProblem> problems) {
		super();

		this.problems = problems;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);

		// Hier wordt het magazijn getekend in 20x10
		for (int y = 0; y <= WAREHOUSE_Y; y++) {
			for (int x = 0; x <= WAREHOUSE_X; x++) {
				g.drawRect((x * CELL_SIZE) + CELL_SIZE, (y * CELL_SIZE),
						CELL_SIZE, CELL_SIZE);
			}
		}
		// Teken de paden die de robotten nemen
		drawPath(g);
	}

	/**
	 * @param g
	 */
	private void drawPath(Graphics g) {
		// lastLocation is de vorige locatie. De eerste locatie is het start
		// punt.
		Location lastLocation = new Location(STARTING_X1, 0);
		int totalDistance = 0;
		// De ruimte tussen de text die de totale afstand weergeeft
		int distanceSpacing = CELL_SIZE;
		// Instellen van 2D graphics om een dikkere lijn te tekenen
		g.setColor(Color.blue);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(LINE_WIDTH));
		// Hier worden de robotten verdeelt van het huidige probleem
		int count = 0;
		for (ArrayList<Product> robot : problems.get(currentProblem)
				.getProblem()) {
			// Teken eerst het begin punt.
			g.fillRect(lastLocation.x * CELL_SIZE + DOT_INDENT + CELL_SIZE,
					(WAREHOUSE_Y - lastLocation.y) * CELL_SIZE + DOT_INDENT,
					PRODUCT_SIZE, PRODUCT_SIZE);
			for (Product product : robot) {
				// Teken het huidige product
				Location productLocation = product.getLocation();
				g.fillRect(productLocation.x * CELL_SIZE + DOT_INDENT
						+ CELL_SIZE, (WAREHOUSE_Y - productLocation.y)
						* CELL_SIZE + DOT_INDENT, PRODUCT_SIZE, PRODUCT_SIZE);
				// Teken een lijn tussen het huidige product en de vorige
				g2D.drawLine(lastLocation.x * CELL_SIZE + LINE_INDENT
						+ CELL_SIZE, (WAREHOUSE_Y - lastLocation.y) * CELL_SIZE
						+ LINE_INDENT, productLocation.x * CELL_SIZE
						+ LINE_INDENT + CELL_SIZE,
						(WAREHOUSE_Y - productLocation.y) * CELL_SIZE
								+ LINE_INDENT);
				// Voeg de afstand tussen het huidige en het vorige product toe
				// bij het totaal
				totalDistance += productLocation.getDistanceTo(lastLocation);
				lastLocation = productLocation;
			}
			// Teken uiteindelijk een lijn tussen het laatste product en het
			// begin punt.
			g2D.drawLine(lastLocation.x * CELL_SIZE + LINE_INDENT + CELL_SIZE,
					(WAREHOUSE_Y - lastLocation.y) * CELL_SIZE + LINE_INDENT,
					(STARTING_X1 + count * (WAREHOUSE_X + 2)) * CELL_SIZE
							+ LINE_INDENT + CELL_SIZE, (WAREHOUSE_Y)
							* CELL_SIZE + LINE_INDENT);
			// En voeg die afstand ook toe aan het totaal
			totalDistance += lastLocation.getDistanceTo(new Location(
					(STARTING_X1 + count * (WAREHOUSE_X + 2)), 0));
			// Schrijf daarna de totale afstand onderaan de pagina. De eerste
			// totale afstand komt links en de tweede komt in het midden.
			g.drawString("Totale afstand: " + totalDistance, distanceSpacing,
					CELL_SIZE * (WAREHOUSE_Y + 1) + 10);
			distanceSpacing += CELL_SIZE * (WAREHOUSE_X + 1) / 2;
			// De kleur veranderen voor de volgende robot
			g.setColor(Color.red);
			// Totaal resetten om die van de andere robot ook te volgen
			totalDistance = 0;
			// Start locatie van de andere robot instellen.
			lastLocation = new Location(WAREHOUSE_X + 1, 0);
			count++;
		}
		g.setColor(Color.black);
		g.drawString("Current problem: " + currentProblem, CELL_SIZE, CELL_SIZE
				* (WAREHOUSE_Y + 1) + 20);
	}

	public void nextProblem() {
		if (currentProblem < problems.size() - 1)
			currentProblem++;
		repaint();
	}

	public void previousProblem() {
		if (currentProblem > 0)
			currentProblem--;
		repaint();
	}
}
