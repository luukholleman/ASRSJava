package asrs;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;

/**
 * De frame voor de simulaties
 * 
 * @author Bas
 */
public class SimulationFrame extends JFrame {
	/**
	 * Het panel voor het warenhuis
	 */
	private OrderPickingPanel warehousePanel;
	/**
	 * Het panel voor de bin packer
	 */
	private BinPackingPanel binPackingPanel;
	
	public SimulationFrame(BinPackingPanel binPackingPanel, OrderPickingPanel warehousePanel){
		setLayout( new FlowLayout() );
		setTitle( "Virtual Auto Dropbox" );
		setVisible( true );
		setSize(800,600);
		
		this.binPackingPanel = binPackingPanel;
		this.warehousePanel = warehousePanel;
		

		binPackingPanel.setPreferredSize(new Dimension(350, 500));
		add(binPackingPanel);
		
		//Starten van de simulatie
		binPackingPanel.start();

		warehousePanel.setPreferredSize(new Dimension(350, 500));
		add(warehousePanel);
		
		//Starten van de simulatie
		warehousePanel.start();
		
		//Als de frame wordt gesloten, stop dan de simulaties.
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				close();
			}
		});
		
		revalidate();
	}
	/**
	 * Stopt de simulaties.
	 * 
	 * @author Bas
	 */
	private void close(){
		binPackingPanel.stop();
		warehousePanel.stop();
	}
	
}
