/**
 * @author Luuk Holleman
 * @date 16 april
 */
package bppAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import order.Product;
import asrs.Bin;

public class AlmostWorstFit implements BPPAlgorithm {
	public static String name = "Almost Worst Fit";

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Bin calculateBin(Product product, ArrayList<Bin> bins) {
		
		//Sorteer de bins in op volgorde van veel inhoud naar weinig inhoud
		Collections.sort(bins, new Comparator<Bin>() {
			public int compare(Bin one, Bin two) {
				return ((Integer) (one.getSize() - one.getFilled()))
						.compareTo(two.getSize() - two.getFilled());
			}
		});
		//Verwijder de bin met de meeste ruimte
		bins.remove(bins.size()-1);
		//Return de bin die daarna de meeste ruimte heeft
		Bin bin = bins.get(bins.size()-1);
		return bin;
	}

}
