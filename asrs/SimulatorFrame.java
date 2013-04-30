package asrs;

import java.awt.*;
import javax.swing.JFrame;

public class SimulatorFrame extends JFrame {
	private OrderPickingSimulatorPanel OPSPanel;
	
	public SimulatorFrame(){
		//setDefaultCloseOperation( EXIT_ON_CLOSE );
		setLayout( new FlowLayout() );
		setTitle( "Simulation" );
		setVisible( true );
		setSize(600,500);
		
		OPSPanel = new OrderPickingSimulatorPanel();
		OPSPanel.setPreferredSize(new Dimension(300, 500));
		add(OPSPanel);
		
		revalidate();
	}
}
