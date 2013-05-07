package asrs;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import order.Location;
import order.Product;

import asrsController.ExecutionManager;

import tspAlgorithm.TSPAlgorithm;

public class OrderPickingPanel extends JPanel implements Runnable {
	//Alle attributen die in meerdere methoden gebruiken (zullen) worden staan hier
//	private ExecutionManager WMan; Wordt nog niet gebruikt
	//	private ArrayList<Location> warenhuis = new ArrayList<Location>(); Deze regel wordt gehouden voor offline testen
	private Thread runner;
	private	ArrayList<Location> warenhuis;
	private Random gen;
	private Location robotLoc;
	private Location robotPix;
	private Location destination;
	private int load = 0;
	
	private ArrayList<Product> products;
	
	public OrderPickingPanel(ArrayList<Product> products){
		super();
		setSize(300,500);
		robotLoc = new Location(0,0);
		robotPix = new Location(0,0);
		try {
			warenhuis = DBHandler.getAllOccupiedLocations();
		} catch (DatabaseConnectionFailedException e){
			JOptionPane.showMessageDialog(this, "Kan geen verbinding maken met de database.");
		}
		gen = new Random();
		destination = warenhuis.get(gen.nextInt(warenhuis.size()));
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		
		//Hier wordt de BinPacking robot getekend (een blok).
		g.drawRect(0, 340, 60, 60);
		
		//Hier wordt het magazijn getekend in 10x20
		for(int y = 0; y < 20; y++){
			for(int x = 0; x < 10; x++){
				g.drawRect(60+(x*20), 0+(y*20), 20, 20);
			}
		}
		
		
		
		//Tekenen van de producten in het warenhuis
		
		for(Location location : warenhuis){
			Location loc = new Location(0,0);
			//De y location wordt hier omgedraait zodat 0,0 links onderin zit.
			loc.y = 19 - location.y;
			loc.x = location.x;
			if(loc.x <= 9 && loc.y <=19){
				g.fillRect(63+(loc.x*20), (loc.y*20)+3, 15, 15);
			}
		}
		
		//Tekenen robot
		
		//De rails
		g.drawLine(0, 420, 260, 420);
		
		//De robot en de ondersteuning worden alleen getekend als de robot bestaat
		if(robotPix != null){
			g.drawRect(robotPix.x, robotPix.y, 18, 18);
			g.drawLine(robotPix.x-1, robotPix.y, robotPix.x-1, 420);
			g.drawLine(robotPix.x+19, robotPix.y, robotPix.x+19, 420);
		}
		
		//Inhoud van de robot tekenen
		if(load == 1) g.fillRect(robotPix.x+5, robotPix.y+5, 8, 8);
		if(load == 2) g.fillRect(robotPix.x+3, robotPix.y+3, 12, 12);
		if(load == 3) g.fillRect(robotPix.x+1, robotPix.y+1, 17, 17);
		
		//Tekenen doel
		g.setColor(Color.blue);
		if(destination != null) g.drawRect(62+(destination.x*20), ((19-destination.y)*20)+2, 16, 16);
	}
	
	/**
	 * Begint de thread om de animatie te laten lopen
	 * 
	 * @param void
	 * @return void
	 */
	public void start(){
		if (runner == null){
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
		Thread thisThread = Thread.currentThread();
		//Aan het begin van de animatie wordt de robot op de bin packing robot gezet.
		robotLoc = new Location (-2,3);
		//De pixel locatie wordt apart van de locatie opgeslagen zodat hij per pixel kan bewegen
		robotPix.x = 61 + (robotLoc.x * 20);
		robotPix.y = 1 + ((19 - robotLoc.y) * 20);
		while (runner == thisThread) {
			//Laat de robot naar de locatie bewegen
			move();
			
			for(Product product : products) {
				//1 seconden stil staan op het product
				frame(100);
				//Als de robot al vol zit, maak hem leeg. Zoniet, stop er 1 'product' in
//				if(load == 3) load = 0;
//				else load++;
				//Haal het opgehaalde product uit het magazijn
								
//				for (Location loc : warenhuis) { 
//					if(loc.x == product.getLocation().x && loc.y == product.getLocation().y) { 
//						warenhuis.remove(loc);
//					}
//				}

					//... Stuur hem naar een willekeurig product in het magazijn
					destination = product.getLocation();
				
				move();
					
				repaint();
				frame();
			}
			
			destination.x = -2;
			destination.y = 3;
			
			move();
			
			repaint();
			frame();
			
			stop();
		}
	}
	
	/**
	 * Stopt de animatie
	 * 
	 * @param void
	 * @return void
	 */
	public void stop(){
		if(runner != null){
			runner = null;
			System.out.println("stopping");
		}
	}
	
	/**
	 * Een functie om de robot van de huidige locatie naar het doel
	 * te bewegen.
	 * 
	 * @param void
	 * @return void
	 */
	private void move(){
		//De beweging wordt verdeelt in stappen over de X en Y as.
		int stepx = destination.x - robotLoc.x;
		int stepy = destination.y - robotLoc.y;
		//De robot wordt naar het doel verplaatst
		robotLoc.x = destination.x;
		robotLoc.y = destination.y;
		//Hier wordt in 20 frames de animatie van de verplaatsing getekent
		for(int i = 0 ; i < 20 ; i++){
			robotPix.x = robotPix.x + stepx;
			robotPix.y = robotPix.y - stepy;
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
	private void frame(){
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) { }
	}
	
	/**
	 * Stop the animation for given amount of milliseconds
	 * 
	 * @param ArrayList<Product>
	 * @return ArrayList<Product>
	 */
	private void frame(int pause){
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) { }
	}
}
