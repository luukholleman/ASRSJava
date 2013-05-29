package taskSimulate;

import java.util.ArrayList;

import bppAlgorithm.Bin;

/**
 * Deze classe bevat alle informatie over een bin packing problem
 * @author timpotze
 *
 */
public class BinPackingProblem {
	
	/**
	 * De bins die in dit probleem zitten
	 */
	private ArrayList<Bin> bins;

	/**
	 * 
	 * @param bins
	 */
	public BinPackingProblem(ArrayList<Bin> bins) {
		this.bins = bins;
	}

	/**
	 * zet de bins in dit probleem
	 * @param bins de bins
	 */
	public void setBins(ArrayList<Bin> bins) {
		this.bins = bins;
	}

	/**
	 * Verkrijg de bins
	 * @return de bins
	 */
	public ArrayList<Bin> getBins() {
		return bins;
	}
}
