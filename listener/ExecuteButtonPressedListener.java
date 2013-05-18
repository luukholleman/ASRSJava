package listener;

import tspAlgorithm.TSPAlgorithm;
import bppAlgorithm.BPPAlgorithm;

public interface ExecuteButtonPressedListener {
	
	/**
	 * Event dat de simulatie knop is ingedrukt
	 * 
	 * @param bpp
	 * @param tsp
	 */
	public abstract void simulatePressed(BPPAlgorithm bpp, TSPAlgorithm tsp);
	
	/**
	 * Event dat de simulatie task knop is ingedrukt
	 * 
	 * @param bpp
	 * @param tsp
	 */
	public abstract void simulateTaskPressed(BPPAlgorithm bpp, TSPAlgorithm tsp, long seed);
	
	/**
	 * Event dat de  knop is ingedrukt
	 * 
	 * @param bpp
	 * @param tsp
	 * @param com1
	 * @param com2
	 */
	public abstract void executePressed(BPPAlgorithm bpp, TSPAlgorithm tsp, String com1, String com2);
}
