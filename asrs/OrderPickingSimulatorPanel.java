package asrs;

import java.awt.*;
import javax.swing.JPanel;

import simulation.WarehouseSimulatorManager;
import tspAlgorithm.TSPAlgorithm;

public class OrderPickingSimulatorPanel extends JPanel {
	private WarehouseSimulatorManager WMan = new WarehouseSimulatorManager();
	int[][] magazijn = new int[20][10];
	
	public OrderPickingSimulatorPanel(TSPAlgorithm tsp){
		super();
		setSize(300,500);
		WMan.run(tsp);
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0,10,10);
		
		for(int y = 0; y <= 20; y++){
			for(int x = 0; x <= 10; x++){
				g.drawRect(0+(x*20), 0+(y*20), 20, 20);
			}
		}
		
	}
}
