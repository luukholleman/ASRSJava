package bppAlgorithm;

import java.util.ArrayList;


public class BinManager {
	private ArrayList<Bin> bins;
	
	public void addBin(Bin bin){
		this.bins.add(bin);
	};
	
	public void clearBins(){
		this.bins.clear();
	};
}