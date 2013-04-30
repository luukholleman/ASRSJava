package asrs;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import asrsController.ExecutionManager;

import tspAlgorithm.TSPAlgorithm;

public class OrderPickingPanel extends JPanel {
	private ExecutionManager WMan = new ExecutionManager();
	int[][] magazijn = new int[20][10];
	
	public OrderPickingPanel(TSPAlgorithm tsp){
		super();
		setSize(300,500);
		WMan.run(tsp);
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
		//Het is mij niet duidelijk hoe ik dingen uit de database kan halen!! 
		//TO-DO
		
		//Aanmaken test gegevens
		
		ArrayList<Product> warenhuis = new ArrayList<Product>();
		Location location1 = new Location(1,1);
		Product product1 = new Product(1, "fiets", 1, 1, location1);
		
		Location location2 = new Location(6,2);
		Product product2 = new Product(2, "fiets", 1, 5, location2);
		
		Location location3 = new Location(9,11);
		Product product3 = new Product(3, "fiets", 1, 8, location3);
		
		Location location4 = new Location(10,20);
		Product product4 = new Product(4, "fiets", 1, 10, location4);
		
		warenhuis.add(product1);
		warenhuis.add(product2);
		warenhuis.add(product3);
		warenhuis.add(product4);
		
		//Tekenen van de producten in het warenhuis
		
		for(Product product : warenhuis){
			Location loc = product.getLocation();
			if(loc.x <= 20 && loc.y <= 30){
				g.fillRect(43+(loc.x*20), (loc.y*20)-17, 15, 15);
			}
		}
		
		
	}
}
