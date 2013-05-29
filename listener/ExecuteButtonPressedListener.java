package listener;

import gnu.io.CommPortIdentifier;
import tspAlgorithm.TSPAlgorithm;
import bppAlgorithm.BPPAlgorithm;

/**
 * 
 * @author Luuk
 *
 * Listener voor start event
 */
public interface ExecuteButtonPressedListener {

	/**
	 * Event dat de simulatie knop is ingedrukt
	 * 
	 * @param bpp Algoritme
	 * @param tsp Algoritme
	 * @param useDetectedSize gebruik de gedetecteerde grote
	 */
	public abstract void simulatePressed(BPPAlgorithm bpp, TSPAlgorithm tsp, Boolean useDetectedSize);

	/**
	 * Start de simulatie met de task
	 * 
	 * @param bpp Algoritme
	 * @param tsp Algoritme
	 * @param seed de seed
	 */
	public abstract void simulateTaskPressed(BPPAlgorithm bpp,
			TSPAlgorithm tsp, long seed);

	/**
	 * Event dat de knop is ingedrukt
	 * 
	 * @param bpp Algoritme
	 * @param tsp Algoritme
	 * @param com1 COM poort
	 * @param com2 COM poort
	 * @param useDetectedSize gebruik de gedetecteerde grote
	 */
	public abstract void executePressed(BPPAlgorithm bpp, TSPAlgorithm tsp,
			CommPortIdentifier com1, CommPortIdentifier com2, Boolean useDetectedSize);
}
