package asrsController;

import order.Product;

public interface BinPacking {
	public abstract void packProduct(Byte binNumber, Product product);
}
