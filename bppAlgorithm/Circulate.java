/**
 * @author Bas van Koesveld
 * @date 24 april
 */
package bppAlgorithm;

import java.util.ArrayList;

import order.Product;

public class Circulate implements BPPAlgorithm {
	private static String name = "Circulate";
	
	@Override
	public String getName() {
		return name;
	}
	/**
	 * Berekend waar het product nog in past
	 * 
	 * @param Product, ArrayList<Bin>
	 * @return Bin
	 */
	@Override
	public Bin calculateBin(Product product, ArrayList<Bin> bins) {
		//Kijken of er nog lege bins in. Zoja, doe het product in deze bin.
		for (Bin bin : bins){
			if(bin.getFilled() != 0 && (bin.getSize()-bin.getFilled()) >= product.getSize()){
				return bin;
			}
		}
		/* Kijken welke bin het minst vol is en het product daar in stoppen.
		 * Als ze allemaal vol zijn, return null.
		*/
		Bin binmin = null;
		for (Bin bin : bins){
			if((binmin == null && (bin.getSize()-bin.getFilled()) >= product.getSize()) || (bin.getFilled() < binmin.getFilled() && (bin.getSize()-bin.getFilled()) >= product.getSize())){
				binmin = bin;
			}
		}		
		return binmin;
	}
	
}
