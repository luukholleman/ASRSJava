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
	
	//Hier wordt de inhoud van het warehouse opgehaald.
	private	DBHandler db;
	private	ArrayList<Location> warenhuis;
//	private ArrayList<Location> warenhuis = new ArrayList<Location>();
	int pause = 50;
	Random gen = new Random();
	Location robotLoc;
	Location robotPix;
	//Destination wordt hier ge-set, dit mag niet en is alleen voor testen.
	Location destination;
	int load = 0;
	boolean moving;
	
	public OrderPickingPanel(TSPAlgorithm tsp){
		super();
		setSize(300,500);
		WMan.run(tsp);
		robotLoc = new Location(0,0);
		robotPix = new Location(0,0);
		
		db = new DBHandler();
		warenhuis = db.getAllOccupiedLocations();
		
//		warenhuis.add(new Location(1,1));
//		warenhuis.add(new Location(2,2));
//		warenhuis.add(new Location(3,3));
		
		destination = warenhuis.get(gen.nextInt(warenhuis.size()));
		
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
		
		
		
		//Tekenen van de producten in het warenhuis
		
		for(Location location : warenhuis){
			Location loc = new Location(0,0);
			loc.y = 19 - location.y;
			loc.x = location.x;
			if(loc.x <= 9 && loc.y <=19){
				g.fillRect(63+(loc.x*20), (loc.y*20)+3, 15, 15);
			}
		}
		
		//Tekenen robot
		
		g.drawLine(0, 420, 260, 420);
		if(robotPix != null){
			g.drawRect(robotPix.x, robotPix.y, 18, 18);
			g.drawLine(robotPix.x-1, robotPix.y, robotPix.x-1, 420);
			g.drawLine(robotPix.x+19, robotPix.y, robotPix.x+19, 420);
		}
		if(load == 1) g.fillRect(robotPix.x+5, robotPix.y+5, 8, 8);
		if(load == 2) g.fillRect(robotPix.x+3, robotPix.y+3, 12, 12);
		if(load == 3) g.fillRect(robotPix.x+1, robotPix.y+1, 17, 17);
		
		//Tekenen doel
		g.setColor(Color.blue);
		if(destination != null) g.drawRect(62+(destination.x*20), ((19-destination.y)*20)+2, 16, 16);
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
			if(robotLoc.getDistanceTo(destination) != 0){
				move();
				if(load == 3) load = 0;
				else load++;
				warenhuis.remove(destination);
				if(load < 3 && warenhuis.size() > 0){
					frame(1000);
					destination = warenhuis.get(gen.nextInt(warenhuis.size()));
					repaint();
					
				}
				else {
					frame(1000);
					destination.x = -2;
					destination.y = 3;
				}
				
			}
			frame();
			
		}
	}

	public void stop(){
		if(runner != null){
			runner = null;
			System.out.println("stopping");
		}
	}
	
	private void move(){
		int stepx = destination.x - robotLoc.x;
		int stepy = destination.y - robotLoc.y;
		robotPix.x = 61 + (robotLoc.x * 20);
		robotPix.y = 1 + ((19 - robotLoc.y) * 20);
		
		robotLoc.x = destination.x;
		robotLoc.y = destination.y;
		for(int i = 0 ; i < 20 ; i++){
			robotPix.x = robotPix.x + stepx;
			robotPix.y = robotPix.y - stepy;
			repaint();
			frame();
		}
	}
	
	private void frame(){
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) { }
	}
	
	private void frame(int pause){
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) { }
	}
}
