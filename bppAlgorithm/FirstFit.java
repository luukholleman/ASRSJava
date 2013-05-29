package bppAlgorithm;

import java.util.ArrayList;

import productInfo.Product;

/**
 * Het First Fit algoritme berekent de eerste bin waar het product in past
 * 
 * @author Mike
 * 
 */
public class FirstFit implements BPPAlgorithm {
	/**
	 * Naam
	 */
	public static final String NAME = "First Fit";
	
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
		// Controleer of het product past
		ArrayList<Bin> possibleBins = new ArrayList<Bin>();
		for (Bin bin : bins) {
			if ((bin.getSize() - bin.getFilled()) >= product.getSize())
				possibleBins.add(bin);
		}
		// Pakt de eerste bin waar het product in past
		Bin fittingBin;
		if (possibleBins.isEmpty())
			fittingBin = null;
		else
			fittingBin = possibleBins.get(0);

		return fittingBin;
	}
}
