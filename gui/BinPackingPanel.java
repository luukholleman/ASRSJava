package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import asrsController.BinPacking;
import asrsController.ExecutionManager;
import bppAlgorithm.Bin;

import productInfo.Product;

public class BinPackingPanel extends JPanel implements Runnable, BinPacking {
	private static final int LINE_COUNT = 13;
	private static final int PRODUCT_SIZE_STRING_ADJUSTMENT = 3;
	private static final int PRODUCT_SIZE = 50;
	private static final int OVERFLOW_LENGTH = 150;
	private static final int OVERFLOW_WIDTH = 300;
	private static final int STRING_INDENT_X = 3;
	private static final int STRING_INDENT_Y = 15;
	private static final int BIN_SIZE = 75;
	private static final int BIN_Y = 200;
	private static final int BIN_VERTICAL_DISTANCE = 100;
	private static final int BIN_HORIZONTAL_DISTANCE = 225;
	private static final int CONVEYOR_X = 100;
	private static final int CONVEYOR_Y = 240;
	private static final int CONVEYOR_LENGTH = 260;
	private static final int CONVEYOR_WIDTH = 100;
	private static final int STRING_SPACE = LINE_COUNT;
	private static final int LINE_DISTANCE = 20;
	private static final int BORDER_Y = 500;

	/**
	 * De Execution Manager
	 */
	private ExecutionManager executionManager;

	/**
	 * De thread die de animatie afspeelt
	 */
	private Thread runner;
	/**
	 * Array van de lijntjes die op de lopende band worden getekent
	 */
	private int lines[];
	/**
	 * De wachtrij van producten die moeten worden ingepakt
	 */
	private ArrayList<Product> productLine = new ArrayList<Product>();
	/**
	 * De hoogte van het product dat op de lopende band staat
	 */
	private int productY;
	/**
	 * Een ArrayList van de bins die in de simulatie staan
	 */
	private ArrayList<Bin> bins;
	/**
	 * Een ArrayList van de wachtrij van bins waar het volgende product in moet
	 */
	private ArrayList<Bin> binLine = new ArrayList<Bin>();
	/**
	 * De volheid van de overflow bin
	 */
	private int overflow;

	/**
	 * Constructor
	 * 
	 * @author Bas
	 */
	public BinPackingPanel() {
		super();
		setSize(300, BORDER_Y);

		/*
		 * De hoogte van de lijntjes die worden getekend op de lopende band
		 * staan in een array.
		 */
		lines = new int[LINE_COUNT];

		// Hier krijgt elke lijn een hoogte
		for (int a = 0; a < lines.length; a++) {
			lines[a] = BORDER_Y - LINE_DISTANCE * a;
		}
		productY = BORDER_Y;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);

		// Tekent de lopende band
		g.drawRect(CONVEYOR_X + STRING_SPACE, CONVEYOR_Y, CONVEYOR_WIDTH,
				CONVEYOR_LENGTH);

		// Tekent de bins
		drawBins(g);

		// Tekent de overflow box en de volheid
		g.drawRect(STRING_SPACE, 0, OVERFLOW_WIDTH, OVERFLOW_LENGTH);

		g.drawString(overflow + "/INFINITY", OVERFLOW_WIDTH / 2 - 5,
				OVERFLOW_LENGTH / 2 - 5);

		// Tekent de lijntjes van de lopende band.
		for (int line : lines) {
			g.drawLine(CONVEYOR_X + STRING_SPACE, line, CONVEYOR_X
					+ STRING_SPACE + CONVEYOR_WIDTH, line);
		}

		// Tekent het product die over de lopende band loopt
		drawProduct(g);
	}

	/**
	 * private method die het product op de lopende band tekent
	 * 
	 * @param graphics
	 * @author Bas
	 */
	private void drawProduct(Graphics g) {
		/*
		 * Het product wordt in het midden van de lopende band getekend door
		 * zijn linker boven hoek de helft van zijn grootte naar links van het
		 * midden van de band te bewegen.
		 */
		g.drawRect(CONVEYOR_X + STRING_SPACE + CONVEYOR_WIDTH / 2
				- PRODUCT_SIZE / 2, productY, PRODUCT_SIZE, PRODUCT_SIZE);
		g.setColor(Color.cyan);
		g.fillRect(CONVEYOR_X + STRING_SPACE + CONVEYOR_WIDTH / 2
				- PRODUCT_SIZE / 2 + 1, productY + 1, PRODUCT_SIZE - 1,
				PRODUCT_SIZE - 2);
		g.setColor(Color.BLACK);
		/*
		 * Als er geen product op de lijn staat kan hij de grootte ervan niet
		 * ophalen. Deze wordt in het midden van het product getekend.
		 */
		if (!productLine.isEmpty())
			g.drawString(Integer.toString(productLine.get(0).getSize()),
					CONVEYOR_X + STRING_SPACE + CONVEYOR_WIDTH / 2
							- PRODUCT_SIZE_STRING_ADJUSTMENT, productY
							+ PRODUCT_SIZE / 2 + PRODUCT_SIZE_STRING_ADJUSTMENT);
	}

	/**
	 * private methode die de bins tekent
	 * 
	 * @param graphics
	 * @author Bas
	 */
	private void drawBins(Graphics g) {
		for (Bin bin : bins) {
			/*
			 * Voor deze formule kreeg ik hulp van Tim. Om de bin wordt er
			 * horizontaal de afstand bij opgeteld en daarna afgetrokken. Om
			 * elke 2 bins worden ze de verticale afstand omlaag gezet.
			 */
			int binx = bins.indexOf(bin) % 2 * BIN_HORIZONTAL_DISTANCE
					+ STRING_SPACE;
			int biny = bins.indexOf(bin) / 2 * BIN_VERTICAL_DISTANCE + BIN_Y;
			// Teken de bin op de berekende X en Y locatie
			g.drawRect(binx, biny, BIN_SIZE, BIN_SIZE);
			// Schrijf de volheid en de grootte bovenop de bin
			g.drawString(bin.getFilled() + "/" + bin.getSize(), binx, biny);
			/*
			 * De diepte geeft aan hoe vol hij al is, zodat er daar bovenop kan
			 * worden getekent
			 */
			int fill = 1;
			// De boolean side wordt om het product omgedraait
			boolean side = false;
			for (Product product : bin.getProducts()) {
				// Bereken hoeveel het product op neemt van de bin
				float productCalc = (float) (BIN_SIZE - 2) / bin.getSize()
						* product.getSize();
				int productDiv = (int) productCalc;
				// Teken eerst de binnenkant van het product
				g.setColor(Color.cyan);
				g.fillRect(binx, biny + BIN_SIZE - fill - productDiv, BIN_SIZE,
						productDiv);
				// Teken daarna een zwarte rand er omheen
				g.setColor(Color.BLACK);
				g.drawRect(binx, biny + BIN_SIZE - fill - productDiv, BIN_SIZE,
						productDiv);
				/*
				 * Opslaan hoe vol de bin is, zodat het volgende product er op
				 * gelegd kan worden
				 */
				fill = fill + productDiv + 1;
				/*
				 * Om elk product wordt de grootte van het product aan de andere
				 * kant van de bin getekent.
				 */
				if (side)
					g.drawString(Integer.toString(product.getSize()), binx
							+ BIN_SIZE + STRING_INDENT_X, biny + BIN_SIZE
							+ STRING_INDENT_Y - fill);
				else
					g.drawString(Integer.toString(product.getSize()), binx
							- STRING_SPACE, biny + BIN_SIZE + STRING_INDENT_Y
							- fill);
				side = !side;
			}
		}
	}

	/**
	 * Begint de thread om de animatie te laten lopen
	 * 
	 * @author Bas
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
	 * @author Bas
	 */
	@Override
	public void run() {
		while (runner != null) {

			// Hier worden de lines per frame 1 pixel omhoog bewogen
			for (int i = 0; i < LINE_COUNT; i++) {
				if (lines[i] > CONVEYOR_Y)
					lines[i]--;
				else
					lines[i] = BORDER_Y;
			}
			// Als er een product is, laat deze dan omhoog bewegen.
			if (!productLine.isEmpty())
				productY--;
			/*
			 * Als het product bovenaan is aangekomen, doe het product in een
			 * bin.
			 */
			if (productY <= CONVEYOR_Y) {
				// Zet het product weer helemaal onderaan
				productY = BORDER_Y;
				// Als het product ergens in kan, doe hem dan daar in.
				if (binLine.get(0) != null)
					binLine.get(0).fill(productLine.get(0));
				// Als het nergens in past, doe hem dan in de overflow.
				else {
					overflow = overflow + productLine.get(0).getSize();
				}
				// Zet in de tabel dat hij is ingepakt.
//				productLine.get(0).setStatus("ingepakt");
//				executionManager.getMain().productStatusUpdated(
//						productLine.get(0));

				// Haal het product en de bin waar hij in moet uit de wachtwij
				productLine.remove(0);
				binLine.remove(0);
			}
			repaint();
			frame();
		}
	}

	public void packProduct(Byte binByte, Product product) {
		// Voeg het product toe aan de wachtrij
		productLine.add(product);
		/*
		 * Als de meegegeven byte gelijk is aan de .size() van de array, geef
		 * dan 'null' mee om aan te geven dat hij nergens in past
		 */
		if (binByte != bins.size())
			binLine.add(bins.get((int) binByte));
		else
			binLine.add(null);
	}

	/**
	 * Stop the animation for 25 milliseconds
	 * 
	 * @author Bas
	 */
	private void frame() {
		/*
		 * Omdat ik niet steeds deze try/catch in de code wil hebben staan, heb
		 * ik hier een private method gemaakt.
		 */
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Stops the simulation
	 * 
	 * @author Bas
	 */
	public void stop() {
		if (runner != null) {
			runner = null;
			System.out.println("stopping Bin Packer");
		}
	}

	/**
	 * Stelt de execution manager in om de bins op te halen,
	 * 
	 * @param ExecutionManager
	 * @author Bas
	 */
	public void setEM(ExecutionManager executionManager) {
		this.executionManager = executionManager;
		bins = new ArrayList<Bin>();
		for (Bin bin : executionManager.getBinManager().bins)
			bins.add(new Bin(bin.getSize(), bin.getFilled()));

	}
}
