package taskSimulate;

import java.util.ArrayList;

import bppAlgorithm.Bin;

/**
 * Deze classe bevat alle informatie over een bin packing problem
 * @author timpotze
 *
 */
public class BinPackingProblem {
	private ArrayList<Bin> bins;

	public BinPackingProblem(ArrayList<Bin> bins) {
		this.bins = bins;
	}

	public void setBins(ArrayList<Bin> bins) {
		this.bins = bins;
	}

	public ArrayList<Bin> getBins() {
		return bins;
	}
}
