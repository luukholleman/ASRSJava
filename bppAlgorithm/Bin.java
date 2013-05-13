package bppAlgorithm;

import order.Product;

public class Bin {
	private int size;
	private int filled;
	
	public Bin(int size, int filled){
		this.size = size;
		this.filled = filled;
	}
	
	public void fill(Product product) {
		if (product.getSize() <= (size - filled)) {
			filled = filled + product.getSize();
		}
	}

	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getFilled() {
		return filled;
	}
	public void setFilled(int filled) {
		this.filled = filled;
	}
}
