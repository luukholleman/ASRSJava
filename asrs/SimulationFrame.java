package asrs;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;

public class SimulationFrame extends JFrame {
	private OrderPickingPanel opPanel;
	private BinPackingPanel bpPanel;
	
	public SimulationFrame(BinPackingPanel bpPanel, OrderPickingPanel opPanel){
		setLayout( new FlowLayout() );
		setTitle( "Simulatie" );
		setVisible( true );
		setSize(800,500);
		
		this.bpPanel = bpPanel;
		this.opPanel = opPanel;
		

		bpPanel.setPreferredSize(new Dimension(350, 500));
		add(bpPanel);
		//Starten van de simulatie
		bpPanel.start();

		opPanel.setPreferredSize(new Dimension(350, 500));
		add(opPanel);
		//Starten van de simulatie
		opPanel.start();
		
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				close();
			}
		});
		
		revalidate();
	}
	private void close(){
		bpPanel.stop();
		opPanel.stop();
	}
	
}
