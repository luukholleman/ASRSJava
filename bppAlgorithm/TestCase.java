package bppAlgorithm;

import java.util.ArrayList;

import order.Product;

import asrs.Bin;
import asrs.Location;

public class TestCase {


	public static void main(String[] args) {
		WorstFit awf = new WorstFit();
		ArrayList<Bin> testBins = new ArrayList<>();

		Bin a = new Bin(60,0);
		Bin b = new Bin(60,10);
		Bin c = new Bin(60,20);
		Bin d = new Bin(60,30);
		Bin e = new Bin(60,40);
		testBins.add(a);
		testBins.add(b);
		testBins.add(c);
		testBins.add(d);
		testBins.add(e);
		
		Location x = new Location(5,5);
		Product z = new Product(1, "Hallo", 15, 6, x);
		
		
		Bin Y = awf.calculateBin(z, testBins);
		System.out.println("De gekozen bin heeft een inhoud van: " + Y.getFilled());
	}

}
