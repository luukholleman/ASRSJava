package taskSimulate;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import order.Location;
import order.Product;

public class OrderPickingTaskSimulation extends JPanel {
	
	
	private int currentProblem = 0;
	private ArrayList<TravelingSalesmanProblem> problems;
	
	public OrderPickingTaskSimulation(ArrayList<TravelingSalesmanProblem> problems){
		
		setPreferredSize(new Dimension(300, 500));
		
		this.problems = problems;
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		
		// Hier wordt het magazijn getekend in 20x10
		for (int y = 0; y <= 19; y++) {
			for (int x = 0; x <= 9; x++) {
				g.drawRect((x * 20), (y * 20), 20, 20);
			}
		}

		Location lastLocation = null;
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(3));
		for(Product product : problems.get(0).getProducts().get(0)){
			Location productLocation = product.getLocation();
			g.drawRect(productLocation.x*20+3, productLocation.y*20+3, 15, 15);
			if(lastLocation != null)	
				g2D.drawLine(lastLocation.x*20+10, lastLocation.y+10*20+10, productLocation.x*20+10, productLocation.y*20+10);
			lastLocation = productLocation;
		}
	}
}
