package asrs;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import asrsController.BinPacking;
import asrsController.ExecutionManager;
import bppAlgorithm.BPPAlgorithm;
import bppAlgorithm.Bin;

import order.Location;
import order.Product;

public class BinPackingPanel extends JPanel implements Runnable, BinPacking {
	private ExecutionManager eM;
	private Thread runner;
	private int lines[];
	private ArrayList<Product> productLine = new ArrayList<Product>();
	private int productHeigth;
	private ArrayList<Bin> bins;
	private int overflow;

	// Constructor
	public BinPackingPanel() {
		super();
		setSize(300, 500);

		lines = new int[13];
		
		for(int a = 0 ; a < lines.length ; a++){
			lines[a] = 500 - 20 * a;
		}
		
		productHeigth = 500;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);

		// Hier wordt de BinPacking robot en alle bins getekent

		// Dit is de lopende band
		g.drawRect(110, 240, 100, 260);

		// Dit zijn de bins
		int b = 0;
		for (Bin bin : bins) {
			int x = b % 2 * 225+10;
			int y = b / 2 * 100 + 200;
			g.drawRect(x, y, 75, 75);
			g.drawString(bin.getFilled() + "/" + bin.getSize(), x, y);	
			int depth = 1;
			boolean side = false;
			for(Product product : bin.getProducts()){
				float productCalc = (float) 73/bin.getSize()*product.getSize();
				int productDiv = (int) productCalc;
				g.setColor(Color.RED);
				g.fillRect(x, y+depth, 75, productDiv);
				g.setColor(Color.BLACK);
				g.drawRect(x, y+depth, 75, productDiv);
				depth = depth + productDiv +1;
				if(side)
					g.drawString(Integer.toString(product.getSize()), x+85, y+depth);
				else
					g.drawString(Integer.toString(product.getSize()), x-10, y+depth);
				side = !side;
			}
			
			if (b >= 4)
				System.out
						.println("Too many bins requested! 4 bins is the maximum amount.");
			b++;
		}

		// Dit is de overflow box
		g.drawRect(10, 0, 300, 150);

		g.drawString(overflow + "/Åá", 145, 70);
		// Tekent de lijntjes van de lopende band.
		for (int line : lines) {
			g.drawLine(110, line, 210, line);
		}
		
		g.drawRect(135, productHeigth, 50, 50);
		g.setColor(Color.WHITE);
		g.fillRect(136, productHeigth+1, 49, 49);
		g.setColor(Color.BLACK);
		if(!productLine.isEmpty())
			g.drawString(Integer.toString(productLine.get(0).getSize()), 148, productHeigth+27);
	}

	/**
	 * Begint de thread om de animatie te laten lopen
	 * 
	 * @param void
	 * @return void
	 */
	public void start() {
		if (runner == null) {
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
		while (runner != null) {

			// Hier worden de lines per frame 1 pixel omhoog bewogen
			for (int i = 0; i < 13; i++) {
				if (lines[i] > 240)
					lines[i]--;
				else
					lines[i] = 500;
			}
			if (!productLine.isEmpty())
				productHeigth--;
			if (productHeigth <= 240) {
				productHeigth = 500;
				byte binByte = 0;
				try{
					bins.get(eM.detectedProduct(binByte, binByte, binByte)).fill(productLine.get(0));
				}
				catch(Exception e){
//					for(Bin bin : bins){
//						overflow = overflow + bin.getFilled();
//						bin.setFilled(0);
//						bin.getProducts().clear();
//					}
					overflow = overflow + productLine.get(0).getSize();
				}
				productLine.get(0).setStatus("ingepakt");
				eM.getMain().productStatusUpdated(productLine.get(0));
				
//				byte methodByte = 0;
//				byte binByte = eM.detectedProduct(methodByte, methodByte, methodByte);
//				if(binByte != null){
//					bins.get(eM.detectedProduct(methodByte, methodByte, methodByte)).fill(productLine.get(0));
//				}
				
				productLine.remove(0);
			}
			repaint();
			frame();
		}

	}

	public void packProducts(ArrayList<Product> products) {
		productLine.addAll(products);
		System.out.println("Bin Packer: Sorting " + productLine.size() + " products.");
	}

	/**
	 * Stop the animation for 25 milliseconds
	 * 
	 * @param void
	 * @return void
	 */
	private void frame() {
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Stop the animation for given amount of milliseconds
	 * 
	 * @param ArrayList
	 *            <Product>
	 * @return ArrayList<Product>
	 */
	private void frame(int pause) {
		try {
			Thread.sleep(pause);
		} catch (InterruptedException e) {
		}
	}
	
	public void stop(){
		if (runner != null) {
			runner = null;
			System.out.println("stopping Bin Packer");
		}
	}

	/**
	 * @param eM
	 *            the eM to set
	 */
	public void setEM(ExecutionManager eM) {
		this.eM = eM;
		bins = eM.getBinManager().bins;
	}
}
