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
		int a = 0;
		int i = 500;
		for (int line : lines) {
			lines[a] = i;
			i = i - 20;
			a++;
		}
		productHeigth = 500;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);

		// Hier wordt de BinPacking robot en alle bins getekent

		// Dit is de lopende band
		g.drawRect(100, 240, 100, 260);

		// Dit zijn de bins
		int b = 0;
		for (Bin bin : bins) {
			if (b == 1) {
				g.drawRect(0, 200, 75, 75);
				g.drawString(bin.getFilled() + "/" + bin.getSize(), 30, 240);
			}
			if (b == 2) {
				g.drawRect(225, 200, 75, 75);
				g.drawString(bin.getFilled() + "/" + bin.getSize(), 255, 240);
			}
			if (b == 3) {
				g.drawRect(0, 300, 75, 75);
				g.drawString(bin.getFilled() + "/" + bin.getSize(), 30, 340);
			}
			if (b == 4) {
				g.drawRect(225, 300, 75, 75);
				g.drawString(bin.getFilled() + "/" + bin.getSize(), 255, 340);
			}
			if (b > 4)
				System.out
						.println("Too many bins requested! 4 bins is the maximum amount.");
			b++;
		}

		// Dit is de overflow box
		g.drawRect(0, 0, 300, 150);
		g.drawString(overflow + "/infinity", 145, 70);

		// Tekent de lijntjes van de lopende band.
		for (int line : lines) {
			g.drawLine(100, line, 200, line);
		}
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
				bins.get(eM.detectedProduct(binByte, binByte, binByte)).fill(
						productLine.get(0));
				productLine.remove(0);
			}
			repaint();
			frame();
		}

	}

	public void packProducts(ArrayList<Product> products) {
		for (Product product : products) {
			productLine.add(product);
		}
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

	/**
	 * @param eM
	 *            the eM to set
	 */
	public void setEM(ExecutionManager eM) {
		this.eM = eM;
		bins = eM.getBinManager().bins;
	}
}
