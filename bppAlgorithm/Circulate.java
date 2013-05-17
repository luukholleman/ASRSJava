/**
 * @author Bas van Koesveld
 * @date 24 april
 */
package bppAlgorithm;

import java.util.ArrayList;

import order.Product;

public class Circulate implements BPPAlgorithm {
	private static String name = "Circulate";
	private Bin lastBin = null;
	
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
		if(lastBin == null){
			lastBin = bins.get(0);
			if(lastBin.getSize() >= product.getSize()){
				System.out.println(Integer.toString(bins.indexOf(lastBin)));
				return lastBin;
			}
		}
		for(Bin bin : bins){
			if(bins.indexOf(lastBin) < bins.size()-1){
				lastBin = bins.get(bins.indexOf(lastBin)+1);
				if(lastBin.getSize()-lastBin.getFilled() >= product.getSize()){
					System.out.println(Integer.toString(bins.indexOf(lastBin)));
					return lastBin;
				}
			}
			else{
				lastBin = bins.get(0);
				if(lastBin.getSize()-lastBin.getFilled() >= product.getSize()){
					System.out.println(Integer.toString(bins.indexOf(lastBin)));
					return lastBin;
				}
			}
			
		}
		return null;
	}
	@Override
	public int getBinCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
