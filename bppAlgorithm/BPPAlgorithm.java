/**
 * @author Luuk Holleman
 * @date 16 april
 */
package bppAlgorithm;

import java.util.ArrayList;

import productInfo.Product;

public interface BPPAlgorithm {
	public abstract Bin calculateBin(Product product, ArrayList<Bin> bins);
}
