package bppAlgorithm;

import java.util.ArrayList;


public class BinManager {
	public ArrayList<Bin> bins;
	
	public BinManager(){
		bins = new ArrayList<Bin>();
	}
	
	public void addBin(Bin bin){
		this.bins.add(bin);
	};
	
	public void clearBins(){
		this.bins.clear();
	};
}