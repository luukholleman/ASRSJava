package bppAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import productInfo.Product;

/**
 * Het Best Fit algoritme berekent welke bin de minste ruimte heeft en of het
 * product er in past
 * 
 * @author Mike
 * 
 */
public class BestFit implements BPPAlgorithm {
	/**
	 * Naam
	 */
	public static final String NAME = "Best Fit";
	
	@Override
	public String getName(){
		return NAME;
	}

	@Override
	/**
	 * Berekent in welke bin het gegeven product moet
	 * 
	 * @param product
	 * @param bins
	 * @return fittingBin
	 */
	public Bin calculateBin(Product product, ArrayList<Bin> bins) {
		// Kopieer de arraylist
		bins = new ArrayList<Bin>(bins);

		// Sorteer de bins in op volgorde van veel inhoud naar weinig inhoud
		Collections.sort(bins, new Comparator<Bin>() {
			public int compare(Bin one, Bin two) {
				return ((Integer) (one.getSize() - one.getFilled()))
						.compareTo(two.getSize() - two.getFilled());
			}
		});
		// Controleer of het product past
		ArrayList<Bin> possibleBins = new ArrayList<Bin>();
		for (Bin bin : bins) {
			if ((bin.getSize() - bin.getFilled()) >= product.getSize())
				possibleBins.add(bin);
		}
		// Return de bin die de minste ruimte heeft
		Bin fittingBin;
		if (possibleBins.isEmpty())
			fittingBin = null;
		else
			fittingBin = possibleBins.get(0);

		return fittingBin;
	}
}
