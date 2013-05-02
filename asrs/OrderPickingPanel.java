package asrs;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import order.Location;
import order.Product;

import asrsController.ExecutionManager;

import tspAlgorithm.TSPAlgorithm;

public class OrderPickingPanel extends JPanel implements Runnable {
	private ExecutionManager WMan = new ExecutionManager();
	private Thread runner;
	int pause = 17;
	Random gen = new Random();
	Location robotLoc;
	Location robotPix;
	//Destination wordt hier ge-set, dit mag niet en is alleen voor testen.
	Location destination = new Location (9,13);
	int load = 1;
	boolean moving;
	
	public OrderPickingPanel(TSPAlgorithm tsp){
		super();
		setSize(300,500);
		WMan.run(tsp);
		robotLoc = new Location(0,0);
		robotPix = new Location(0,0);
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		
		//Hier wordt de BinPacking robot (blok) en het magazijn getekend.
		g.drawRect(0, 340, 60, 60);
		for(int y = 0; y < 20; y++){
			for(int x = 0; x < 10; x++){
				g.drawRect(60+(x*20), 0+(y*20), 20, 20);
			}
		}
		
		//Hier wordt de inhoud van het warehouse opgehaald.
//		DBHandler db = new DBHandler();
//		ArrayList<Location> locations = db.getAllOccupiedLocations();
		
		//Aanmaken test gegevens
		
		ArrayList<Product> warenhuis = new ArrayList<Product>();
		Location location1 = new Location(0,0);
		Product product1 = new Product(1, "fiets", 1, 1, location1);
		
		Location location2 = new Location(6,2);
		Product product2 = new Product(2, "fiets", 1, 5, location2);
		
		Location location3 = new Location(9,11);
		Product product3 = new Product(3, "fiets", 1, 8, location3);
		
		Location location4 = new Location(9,19);
		Product product4 = new Product(4, "fiets", 1, 10, location4);
		
		warenhuis.add(product1);
		warenhuis.add(product2);
		warenhuis.add(product3);
		warenhuis.add(product4);
		
		//Tekenen van de producten in het warenhuis
		
		for(Product product : warenhuis){
			Location loc = product.getLocation();
			loc.y = 19 - loc.y;
			if(loc.x <= 9 && loc.y <=19){
				g.fillRect(63+(loc.x*20), (loc.y*20)+3, 15, 15);
			}
		}
		
		//Tekenen robot
		
		if(robotPix != null) g.drawRect(robotPix.x, robotPix.y, 18, 18);
		
		//Tekenen doel
		g.setColor(Color.blue);
		if(destination != null) g.drawRect(62+(destination.x*20), ((19-destination.y)*20)+2, 16, 16);
		//Ophalen van de gegeven order
		//TO-DO
		
		//Testgegevens
		
//		ArrayList<Product> order = new ArrayList<Product>();
//		order.add(product1);
//		order.add(product2);
//		order.add(product3);
	}
	
	public void start(){
		if (runner == null){
			runner = new Thread(this);
			runner.start();
		}
	}
	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();
		robotLoc = new Location (0,0);
		robotPix.x = 61 + (robotLoc.x * 20);
		robotPix.y = 1 + ((19 - robotLoc.y) * 20);
		
		while (runner == thisThread) {
			//Location 1 is de huidige postitie, location2 is het doel.
			if(robotLoc.getDistanceTo(destination) > 0){
				int storex = robotLoc.x;
				int storey = robotLoc.y;
				move();
				
				int compix = 61 + (robotLoc.x * 20);
				int compiy = 1 + ((19 - robotLoc.y) * 20);
				if(storex != robotLoc.x){
					int step = (robotLoc.x - storex) * 4;
					while(robotPix.x != compix){
						System.out.println("moving " + step + "...");
						robotPix.x = robotPix.x + step;
						repaint();
						try {
							Thread.sleep(pause);
						} catch (InterruptedException e) { }
					}
					System.out.println("Finished moving");
				}
				if(storey != robotLoc.y){
					int step = (storey - robotLoc.y) * 4;
					while(robotPix.y != compiy){
						System.out.println("moving...");
						robotPix.y = robotPix.y + step;
						repaint();
						try {
							Thread.sleep(pause);
						} catch (InterruptedException e) { }
					}
					System.out.println("Finished moving");
				}
				System.out.println("Ended moving");
				
			}
			else{
				load++;
				if(load <= 3){
					destination.x = gen.nextInt(10);
					destination.y = gen.nextInt(20);
					System.out.println("Generated new destination");
				}
				else {
					destination.x = -2;
					destination.y = 3;
					load = 0;
				}
			}
			repaint();
			try {
				Thread.sleep(pause);
			} catch (InterruptedException e) { }
//			stop();
			
		}
	}

	public void stop(){
		if(runner != null){
			runner = null;
			System.out.println("stopping");
		}
	}
	
	private void move(){
		int distx = destination.x - robotLoc.x;
		int disty = destination.y - robotLoc.y;
		int compy = 0;
		int compx = 0;
		if (disty < 0) compy = disty * -1;
		else compy = disty;
		if (distx < 0) compx = distx * -1;
		else compx = distx;
		if (compy < compx) {
			if (distx > 0){
				robotLoc.x = robotLoc.x + 1;
				System.out.println("Moved right");
			}
			else{
				robotLoc.x = robotLoc.x -1;
				System.out.println("Moved left");
			}
		}
		if (compy > compx) {
			if (disty > 0){
				//Here, the system claims the robot moved down. This is because it DID move down. The simulation has flipped Y-axis.
				robotLoc.y = robotLoc.y + 1;
				System.out.println("Moved down");
			}
			else{
				robotLoc.y = robotLoc.y -1;
				System.out.println("Moved up");
			}
		}
		if (compy == compx){
			if (disty > 0){
				robotLoc.y = robotLoc.y + 1;
				System.out.println("Moved down");
			}
			else{
				robotLoc.y = robotLoc.y -1;
				System.out.println("Moved up");
			}
		}
		System.out.println("Coordinaten: " + robotLoc.x + " " + robotLoc.y + " en " + destination.x + " " + destination.y);
	}
}
