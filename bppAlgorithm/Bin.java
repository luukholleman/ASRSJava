package bppAlgorithm;

import java.util.ArrayList;

import productInfo.Product;

public class Bin {
	private int size;
	private int filled;
	
	private ArrayList<Product> products;
	public Bin(int size, int filled){
		this.size = size;
		this.filled = filled;
		
		products = new ArrayList<Product>();
	}
	
	public void fill(Product product) {
		if (product.getSize() <= (size - filled)) {
			filled += product.getSize();
		}
		products.add(product);
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
	
	public ArrayList<Product> getProducts()
	{
		return products;
	}
}
