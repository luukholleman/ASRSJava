package asrs;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import asrsController.ExecutionManager;
import bppAlgorithm.BPPAlgorithm;

import order.Location;
//Javadoc voor de class
//Variabelen naamgeving
//Veel voorkomende waardes van tevoren declareren.
//Langere methods in sub-methods verdelen
//Magic numbers

public class BinPackingPanel extends JPanel implements Runnable {
	private Thread runner;
	private int lines[];
	
	//Constructor
	public BinPackingPanel(ExecutionManager eM){
		super();
		setSize(300,500);
		setLines(new int[13]);
		int a = 0;
		int i = 500;
		for(int line : getLines()){
			getLines()[a] = i;
			i = i-20;
			a++;
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		
		//Hier wordt de BinPacking robot en alle bins getekent
		
		//Dit is de lopende band
		g.drawRect(100, 240, 100, 260);
		
		//Dit zijn de bins
		g.drawRect(0, 200, 75, 75);
		g.drawRect(225, 200, 75, 75);
		g.drawString("1/5", 30, 240);
		g.drawString("1/5", 255, 240);
		
		//Dit is de overflow box
		g.drawRect(0, 0, 300, 150);
		g.drawString("1/inf", 145, 70);
		
		//Tekent de lijntjes van de lopende band.
		for(int line : getLines()){
			g.drawLine(100, line, 200, line);
		}		
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
		while (runner != null){
			
			//Hier worden de lines per frame 1 pixel omhoog bewogen
			for(int i = 0 ; i < 13 ; i++){
				if(getLines()[i] > 240){
					getLines()[i]--;
				}
				else{
					getLines()[i] = 500;
				}
			}
			repaint();
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) { }
		}
		
	}
	
	//Noem frame sleep
	
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

	public int[] getLines() {
		return lines;
	}

	public void setLines(int lines[]) {
		this.lines = lines;
	}
}
