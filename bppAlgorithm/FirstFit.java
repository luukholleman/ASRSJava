/**
 * @author Luuk Holleman
 * @date 16 april
 */
package bppAlgorithm;

import java.util.ArrayList;

import productInfo.Product;

public class FirstFit implements BPPAlgorithm {
	public static String name = "First Fit";

	@Override
	/**
	 * Geeft de naam van het algoritme aan de GUI
	 * @return name
	 */
	public String getName() {
		return name;
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
		//Controleer of het product past
		ArrayList<Bin> possibleBins = new ArrayList<Bin>();
		for(Bin bin : bins){
			if ((bin.getSize()-bin.getFilled()) >= product.getSize())
				possibleBins.add(bin);
		}
		//Pakt de eerste bin waar het product in past
		Bin fittingBin;
		if(possibleBins.isEmpty())
			fittingBin = null;
		else
			fittingBin = possibleBins.get(0);
		
		return fittingBin;
	}

	@Override
	public int getBinCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
