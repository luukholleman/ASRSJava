package asrs;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import bppAlgorithm.BPPAlgorithm;

import order.Location;

public class BinPackingPanel extends JPanel implements Runnable {
	private Thread runner;
	private int lines[];
	
	
	public BinPackingPanel(BPPAlgorithm bpp){
		super();
		setSize(300,500);
		lines = new int[13];
		int a = 0;
		int i = 500;
		for(int line : lines){
			lines[a] = i;
			i = i-20;
			a++;
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		
		//Hier wordt de BinPacking robot (blok) en het magazijn getekend.
		g.drawRect(100, 240, 100, 260);
		g.drawRect(0, 200, 75, 75);
		g.drawRect(225, 200, 75, 75);
		g.drawRect(0, 0, 300, 150);
		g.drawString("1/5", 30, 240);
		g.drawString("1/5", 255, 240);
		g.drawString("1/inf", 145, 70);
		for(int line : lines){
			g.drawLine(100, line, 200, line);
		}		
	}
	
	public void start(){
		if (runner == null){
			runner = new Thread(this);
			runner.start();
		}
	}
	
	@Override
	public void run() {
		while (runner != null){
			for(int i = 0 ; i < 13 ; i++){
				if(lines[i] > 240){
					lines[i]--;
				}
				else{
					lines[i] = 500;
				}
			}
			repaint();
			frame();
		}
		
	}
	
	private void frame(){
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) { }
	}
	
	private void frame(int pause){
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) { }
	}
}
