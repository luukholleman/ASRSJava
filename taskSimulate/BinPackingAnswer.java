package taskSimulate;

import bppAlgorithm.Bin;
import order.Product;

public class BinPackingAnswer {
	private Product product;
	private Bin bin;
	
	public BinPackingAnswer(Bin bin, Product product){
		this.setBin(bin);
		this.setProduct(product);
	}

	/**
	 * @return the bin
	 */
	public Bin getBin() {
		return bin;
	}

	/**
	 * @param bin the bin to set
	 */
	public void setBin(Bin bin) {
		this.bin = bin;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
}
