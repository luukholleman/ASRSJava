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
	// Alle attributen die in meerdere methoden gebruiken (zullen) worden staan
	// hier
	private BinPackingPanel bpPanel;
	private ExecutionManager eM;
	private Thread runner;
	private ArrayList<Location> warehouse;
	private ArrayList<Product> products;
	private Random gen;
	private WarehouseRobot robots[];
	private WarehouseRobot robotLeft;
	private WarehouseRobot robotRight;
	private Location destination;
	private int load = 0;
	private int check = 0;
	
	public OrderPickingPanel(BinPackingPanel bpPanel) {
		super();
		setSize(300, 500);
		this.bpPanel = bpPanel;
		robotLeft = new WarehouseRobot(new Location(0, 0), 0);
		robotLeft.pixels = new Location(61 + (robotLeft.loc.x * 20),
				1 + ((19 - robotLeft.loc.y) * 20));
		robotRight = new WarehouseRobot(new Location(9, 0), 1);
		robotRight.pixels = new Location(61 + (robotRight.loc.x * 20),
				1 + ((19 - robotRight.loc.y) * 20));
		robots = new WarehouseRobot[2];
		robots[0] = robotLeft;
		robots[1] = robotRight;
		try {
			warehouse = DBHandler.getAllOccupiedLocations();
		} catch (DatabaseConnectionFailedException e) {
			JOptionPane.showMessageDialog(this,
					"Kan geen verbinding maken met de database.");
		}
		gen = new Random();
		destination = warehouse.get(gen.nextInt(warehouse.size()));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);

		// Hier wordt de BinPacking robot getekend (een blok).
		g.drawRect(0, 340, 60, 60);

		// Hier wordt het magazijn getekend in 10x20
		for (int y = 0; y < 20; y++) {
			for (int x = 0; x < 10; x++) {
				g.drawRect(60 + (x * 20), 0 + (y * 20), 20, 20);
			}
		}

		// Tekenen van de producten in het warenhuis
		drawWarehouseProducts(g);

		// Tekenen robots
		drawRobots(g);

		// Tekenen doel
		drawDestination(g);
	}

	private void drawDestination(Graphics g) {
		g.setColor(Color.blue);
		if (robots[0].destination != null)
			g.drawRect(62 + (robots[0].destination.x * 20),
					((19 - robots[0].destination.y) * 20) + 2, 16, 16);

		if (robots[1].destination != null)
			g.drawRect(62 + (robots[1].destination.x * 20),
					((19 - robots[1].destination.y) * 20) + 2, 16, 16);
	}

	private void drawRobots(Graphics g) {
		// De rails
		g.drawLine(0, 420, 260, 420);

		// De robots en de ondersteuning worden alleen getekend als de robot
		// bestaat
		for (WarehouseRobot robot : robots) {
			if (robot != null) {
				g.drawRect(robot.pixels.x, robot.pixels.y, 18, 18);
				g.drawLine(robot.pixels.x - 1, robot.pixels.y,
						robot.pixels.x - 1, 420);
				g.drawLine(robot.pixels.x + 19, robot.pixels.y,
						robot.pixels.x + 19, 420);

			}
		}

		// Inhoud van de robots tekenen
		for (WarehouseRobot robot : robots) {
			if (robot.load == 1)
				g.fillRect(robot.loc.x + 5, robot.loc.y + 5, 8, 8);
			if (robot.load == 2)
				g.fillRect(robot.loc.x + 3, robot.loc.y + 3, 12, 12);
			if (robot.load == 3)
				g.fillRect(robot.loc.x + 1, robot.loc.y + 1, 17, 17);
		}
	}

	private void drawWarehouseProducts(Graphics g) {
		for (Location location : warehouse) {
			Location loc = new Location(0, 0);
			// De y location wordt hier omgedraait zodat 0,0 links onderin zit.
			loc.y = 19 - location.y;
			loc.x = location.x;
			if (loc.x <= 9 && loc.y <= 19) {
				g.fillRect(63 + (loc.x * 20), (loc.y * 20) + 3, 15, 15);
			}
		}
	}

	/**
	 * Begint de thread om de animatie te laten lopen
	 * 
	 * @param void
	 * @return void
	 */
	public void start() {
		if (runner == null) {
			runner = new Thread(this);
			runner.start();
		}
	}

	/**
	 * Laat de animatie afspelen
	 * 
	 * @param void
	 * @return void
	 */
	@Override
	public void run() {
		Order order = eM.getOrder();
		products = order.getProducts();
		Thread thisThread = Thread.currentThread();
		while (runner == thisThread) {
			if (robots[0].load <= 3 && robots[1].load <= 3) {
				if (check != 0) 
					for(WarehouseRobot robot : robots)
						if (robot.finished == false)
							eM.pickedUpProduct(robot.id);
				move();
				frame(1000);
				warehouse.remove(robots[0].destination);
				warehouse.remove(robots[1].destination);
				for (WarehouseRobot robot : robots) {
					if (robot.loc.x == -2 && robot.loc.y == 3) {
						bpPanel.packProducts(robot.productsOnFork);
						eM.deliveredProduct(robot);
					}	else
							for(Product product : products)
								if(robot.destination == product.getLocation()){
									robot.productsOnFork.add(product);
									product.setStatus("opgepakt");
									eM.getMain().productStatusUpdated(product);
								}

				}
				System.out.println("Loop " + check);
				check++;

				warehouse.indexOf(robots[0].destination);

			} else {
				robots[0].destination = new Location(-2, 3);
				robots[1].destination = new Location(-2, 3);
				move();
				for(WarehouseRobot robot : robots)
					for(Product product : products)
						if(robot.destination == product.getLocation())
							robot.productsOnFork.add(product);

			}
		}
	}

	/**
	 * Stopt de animatie
	 * 
	 * @param void
	 * @return void
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
		for (int i = 0; i < 20; i++) {
			robots[0].pixels.x = robots[0].pixels.x + stepx0;
			robots[0].pixels.y = robots[0].pixels.y - stepy0;

			robots[1].pixels.x = robots[1].pixels.x + stepx1;
			robots[1].pixels.y = robots[1].pixels.y - stepy1;
			repaint();
			frame();
		}
	}

	/**
	 * Stop the animation for 25 milliseconds
	 * 
	 * @param void
	 * @return void
	 */
	private void frame() {
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Stop the animation for given amount of milliseconds
	 * 
	 * @param ArrayList
	 *            <Product>
	 * @return ArrayList<Product>
	 */
	private void frame(int pause) {
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void retrieveProduct(Location location, int robotId) {
		robots[robotId].destination = location;
	}

	@Override
	public void bringToBinPacker(int robotID) {
		robots[robotID].destination = new Location(-2, 3);
	}

	@Override
	public void moveToStart(int robotId) {
		robots[robotId].destination = getStartLocation(robotId);
		move();
		frame();
		robots[robotId].finished = true;
		if (robots[0].finished == true && robots[1].finished == true) {
			stop();
		}
	}

	@Override
	public Integer getNumberOfRobots() {
		return 2;
	}

	@Override
	public Location getStartLocation(int r) {
		if (r == 0) {
			return new Location(0, 0);
		} else if (r == 1) {
			return new Location(9, 0);
		} else {
			return null;
		}

	}

	/**
	 * @param eM
	 *            the eM to set
	 */
	public void setEM(ExecutionManager eM) {
		this.eM = eM;
	}
}
