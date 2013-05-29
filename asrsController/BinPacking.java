package asrsController;

import productInfo.Product;

public interface BinPacking {
	public abstract void packProduct(Byte binNumber, Product product);
}
