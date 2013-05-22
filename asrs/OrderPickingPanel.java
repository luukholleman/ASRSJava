/** 
 * @author Bas van Koesveld
 */

package asrs;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import order.Location;
import order.Order;
import order.Product;

import asrsController.ExecutionManager;
import asrsController.WarehouseRobot;
import asrsController.Warehouse;

public class OrderPickingPanel extends JPanel implements Runnable, Warehouse {
	private static final int ROBOT_INDENT = 1;
	private static final int FRAME_TIME = 25;
	private static final int BINPACKER_Y = 3;
	private static final int BINPACKER_X = -2;
	private static final int PAUSE_TIME_ON_PICKUP = 1000;
	private static final int LOAD_MAX = BINPACKER_Y;
	private static final int PRODUCT_SIZE = 15;
	private static final int PRODUCT_INDENT = LOAD_MAX;
	private static final int ROBOT_SIZE = 18;
	private static final int RAILS_X = 260;
	private static final int RAILS_Y = 420;
	private static final int DESTINATION_SIZE = 16;
	private static final int DESTINATION_INDENT = 2;
	private static final int WAREHOUSE_MAX_Y = 19;
	private static final int CELL_SIZE = 20;
	private static final int WAREHOUSE_MAX_X = 9;
	private static final int BINPACKER_SIZE = 60;
	private static final int BINPACKER_DEPTH = 340;
	

	/**
	 * De panel van de bin packer
	 */
	private BinPackingPanel binPackingPanel;
	/**
	 * De ExecutionManager die alle data opslaat en simulatie gerelateerde
	 * berekeningen uitvoert
	 */
	private ExecutionManager executionManager;
	/**
	 * De thread die de animatie laat aflopen
	 */
	private Thread runner;
	/**
	 * In warehouse worden alle locaties van de producten in het warenhuis
	 * opgeslagen
	 */
	private ArrayList<Location> warehouse;
	/**
	 * Alle producten die moeten worden opgehaald
	 */
	private ArrayList<Product> products;
	/**
	 * Array met beide robots
	 */
	private WarehouseRobot robots[];
	/**
	 * De linker warehouse robot
	 */
	private WarehouseRobot robotLeft;
	/**
	 * De rechter warehouse robot
	 */
	private WarehouseRobot robotRight;

	/**
	 * constructor
	 * 
	 * @param bpPanel
	 * @author Bas
	 */
	public OrderPickingPanel(BinPackingPanel bpPanel) {
		super();
		setSize(300, 500);

		/*
		 * Het BinPackingPanel wordt in het OrderPickingPanel opgeslagen zodat
		 * het Order Picker kan zeggen wanneer er producten naar de Bin Packer
		 * moeten worden gestuurd.
		 */
		this.binPackingPanel = bpPanel;
		// Beide robots worden aangemaakt en op hun begin plek gezet.
		robotLeft = new WarehouseRobot(getStartLocation(0), 0);
		robotLeft.pixels = new Location(BINPACKER_SIZE + ROBOT_INDENT
				+ (robotLeft.loc.x * CELL_SIZE), ROBOT_INDENT
				+ ((WAREHOUSE_MAX_Y - robotLeft.loc.y) * CELL_SIZE));
		robotRight = new WarehouseRobot(getStartLocation(1), 1);
		robotRight.pixels = new Location(BINPACKER_SIZE + ROBOT_INDENT
				+ (robotRight.loc.x * CELL_SIZE), ROBOT_INDENT
				+ ((WAREHOUSE_MAX_Y - robotRight.loc.y) * CELL_SIZE));
		// Beide robots worden in een array gezet om makkelijk aan te roepen.
		robots = new WarehouseRobot[2];
		robots[0] = robotLeft;
		robots[1] = robotRight;
		/*
		 * Alle producten worden uit het warenhuis gehaald zodat ze kunnen
		 * getoont worden.
		 */
		try {
			warehouse = Database.getAllOccupiedLocations();
		} catch (DatabaseConnectionFailedException e) {
			JOptionPane.showMessageDialog(this,
					"Kan geen verbinding maken met de database.");
		}
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);

		// Hier wordt de BinPacking robot getekend (een blok).
		g.drawRect(0, BINPACKER_DEPTH, BINPACKER_SIZE, BINPACKER_SIZE);

		// Hier wordt het magazijn getekend in 10x20
		for (int y = 0; y <= WAREHOUSE_MAX_Y; y++) {
			for (int x = 0; x <= WAREHOUSE_MAX_X; x++) {
				g.drawRect(BINPACKER_SIZE + (x * CELL_SIZE), (y * CELL_SIZE),
						CELL_SIZE, CELL_SIZE);
			}
		}

		// Tekenen van de producten in het warenhuis
		drawWarehouseProducts(g);

		// Tekenen robots
		drawRobots(g);

		// Tekenen doel
		drawDestination(g);
	}

	/**
	 * Tekent de bestemming van elke robot in de vorm van een blauw blokje
	 * 
	 * @param Graphics
	 * @author Bas
	 */
	private void drawDestination(Graphics g) {
		g.setColor(Color.blue);
		for (WarehouseRobot robot : robots)
			if (robot.destination != null)
				g.drawRect(
						BINPACKER_SIZE + DESTINATION_INDENT
								+ (robot.destination.x * CELL_SIZE),
						((WAREHOUSE_MAX_Y - robot.destination.y) * CELL_SIZE)
								+ DESTINATION_INDENT, DESTINATION_SIZE,
						DESTINATION_SIZE);
	}

	/**
	 * Tekent de robots
	 * 
	 * @param Graphics
	 * @author Bas
	 */
	private void drawRobots(Graphics g) {
		// De rails
		g.drawLine(0, RAILS_Y, RAILS_X, RAILS_Y);

		/*
		 * De robots en de ondersteuning worden alleen getekend als de robot
		 * bestaat
		 */
		for (WarehouseRobot robot : robots) {
			if (robot != null) {
				g.drawRect(robot.pixels.x, robot.pixels.y, ROBOT_SIZE,
						ROBOT_SIZE);
				g.drawLine(robot.pixels.x - 1, robot.pixels.y,
						robot.pixels.x - 1, RAILS_Y);
				g.drawLine(robot.pixels.x + ROBOT_SIZE + 1, robot.pixels.y,
						robot.pixels.x + ROBOT_SIZE + 1, RAILS_Y);

			}
		}

		// Inhoud van de robots tekenen
		for (WarehouseRobot robot : robots) {
			g.fillRect(robot.loc.x + 7 - (robot.load * 2), robot.loc.y + 7
					- (robot.load * 2), LOAD_MAX + (robot.load * 5), LOAD_MAX
					+ (robot.load * 5));
		}
	}

	/**
	 * Tekent alle producten in het warenhuis
	 * 
	 * @param Graphics
	 * @author Bas
	 */
	private void drawWarehouseProducts(Graphics g) {
		for (Location location : warehouse) {
			Location loc = new Location(0, 0);
			// De y location wordt hier omgedraait zodat 0,0 links onderin zit.
			loc.y = WAREHOUSE_MAX_Y - location.y;
			loc.x = location.x;
			if (loc.x <= WAREHOUSE_MAX_X && loc.y <= WAREHOUSE_MAX_Y) {
				g.fillRect(BINPACKER_SIZE + PRODUCT_INDENT
						+ (loc.x * CELL_SIZE), (loc.y * CELL_SIZE)
						+ PRODUCT_INDENT, PRODUCT_SIZE, PRODUCT_SIZE);
			}
		}
	}

	/**
	 * Begint de thread om de animatie te laten lopen
	 * 
	 * @param void
	 * @return void
	 * @author Bas
	 */
	public void start() {
		if (runner == null) {
			runner = new Thread(this);
			runner.start();
		}
	}

	/**
	 * Dit is de loop die afloopt om de animatie af te spelen
	 * 
	 * @param void
	 * @return void
	 * @author Bas
	 */
	@Override
	public void run() {
		// De producten die onderdeel zijn worden uit de order gehaald
		Order order = executionManager.getOrder();
		products = order.getProducts();

		// Deze regel komt van het internet. Ik begrijp nog steeds threads niet
		// volledig, sorry.
		Thread thisThread = Thread.currentThread();
		while (runner == thisThread) {
			// Als de robots niet vol zijn, haal het volgende product op.
			if (robots[0].load <= LOAD_MAX || robots[1].load <= LOAD_MAX) {
				for (WarehouseRobot robot : robots)
					if (robot.finished == false)
						executionManager.pickedUpProduct(robot.id);
				move();
				sleep(PAUSE_TIME_ON_PICKUP);

				// Als de robot de producten heeft opgepakt, haal ze uit het
				// warenhuis en leg ze op de robot of op de bin packer.
				warehouse.remove(robots[0].destination);
				warehouse.remove(robots[1].destination);
				for (WarehouseRobot robot : robots) {
					if (robot.loc.x == BINPACKER_X && robot.loc.y == LOAD_MAX)
						executionManager.deliveredProduct(robot);
					else
						for (Product product : products)
							if (robot.destination == product.getLocation()) {
								robot.productsOnFork.add(product);
								product.setStatus("opgepakt");
								executionManager.getMain().productStatusUpdated(
										product);
							}

				}

				// Als een van de robots vol is, laat deze robot dan naar de
				// bin packer bewegen.
			} else {
				if (robots[0].load <= LOAD_MAX)
					robots[0].destination = new Location(BINPACKER_X,
							BINPACKER_Y);
				if (robots[1].load <= LOAD_MAX)
					robots[1].destination = new Location(BINPACKER_X,
							BINPACKER_Y);
				move();
				// Leg zijn producten daarna op de bin packer.
				for (WarehouseRobot robot : robots)
					for (Product product : products)
						if (robot.destination == product.getLocation())
							robot.productsOnFork.add(product);

			}
		}
	}

	/**
	 * Stopt de animatie
	 * 
	 * @param void
	 * @return void
	 * @author Bas
	 */
	public void stop() {
		if (runner != null) {
			runner = null;
			System.out.println("stopping Order Picker");
		}
	}

	/**
	 * Een functie om de robot van de huidige locatie naar het doel te bewegen.
	 * 
	 * @param void
	 * @return void
	 * @author Bas
	 */
	private void move() {
		// De beweging wordt verdeelt in stappen over de X en Y as.
		int stepx0 = robots[0].destination.x - robots[0].loc.x;
		int stepy0 = robots[0].destination.y - robots[0].loc.y;

		int stepx1 = robots[1].destination.x - robots[1].loc.x;
		int stepy1 = robots[1].destination.y - robots[1].loc.y;
		// De robot wordt naar het doel verplaatst
		robots[0].loc = robots[0].destination;
		robots[1].loc = robots[1].destination;
		// Hier wordt in 20 frames de animatie van de verplaatsing getekent
		for (int i = 0; i < CELL_SIZE; i++) {
			robots[0].pixels.x = robots[0].pixels.x + stepx0;
			robots[0].pixels.y = robots[0].pixels.y - stepy0;
			
			robots[1].pixels.x = robots[1].pixels.x + stepx1;
			robots[1].pixels.y = robots[1].pixels.y - stepy1;
			repaint();
			sleep();
		}
	}

	/**
	 * Stopt de animatie voor 25 miliseconden
	 * 
	 * @param void
	 * @return void
	 * @author Bas
	 */
	private void sleep() {
		try {
			Thread.sleep(FRAME_TIME);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Stopt de animatie voor een variabel aantal miliseconden
	 * 
	 * @param int
	 * @return void
	 * @author Bas
	 */
	private void sleep(int pause) {
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * @see asrsController.Warehouse#retrieveProduct(order.Location, int)
	 */
	@Override
	public void retrieveProduct(Location location, int robotId) {
		robots[robotId].destination = location;
	}

	/**
	 * @see asrsController.Warehouse#bringToBinPacker(int)
	 */
	@Override
	public void bringToBinPacker(int robotID) {
		robots[robotID].destination = new Location(BINPACKER_X, LOAD_MAX);
	}

	/**
	 * @see asrsController.Warehouse#moveToStart(int)
	 */
	@Override
	public void moveToStart(int robotId) {
		robots[robotId].destination = getStartLocation(robotId);
		move();
		sleep();
		robots[robotId].finished = true;
		if (robots[0].finished == true && robots[1].finished == true)
			stop();
	}

	/**
	 * @see asrsController.Warehouse#getProblem()
	 */
	@Override
	public Integer getNumberOfRobots() {
		return 2;
	}

	/**
	 * @see asrsController.Warehouse#getStartLocation(int)
	 */
	@Override
	public Location getStartLocation(int robotId) {
		if (robotId == 0) {
			return new Location(-1, 0);
		} else if (robotId == 1) {
			return new Location(10, 0);
		} else {
			return null;
		}

	}

	/**
	 * EM setter
	 * 
	 * @param eM
	 * @author Bas
	 */
	public void setEM(ExecutionManager eM) {
		this.executionManager = eM;
	}
}
