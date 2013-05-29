package asrsController;

import productInfo.Location;

/**
 * Interface dat gebruikt word voor de simulaties en de Arduino  
 * 
 * @author Mike
 * 
 */
public interface Warehouse {
	/**
	 * Stuurt de robot naar het volgende product
	 * 
	 * @param location
	 * @param robotId
	 */
	public abstract void retrieveProduct(Location location, int robotId);
	
	/**
	 * Zet het pakketje op de binpacker
	 * 
	 * @param robotId
	 */
	public abstract void bringToBinPacker(int robotId);
	
	/**
	 * Breng de robot naar de binpacker
	 * 
	 * @param robotId
	 */
	public abstract void moveToStart(int robotId);
	
	/**
	 * Geeft het aantal robots terug
	 * 
	 * @return 
	 */
	public abstract int getNumberOfRobots();
	
	/**
	 * Geeft de startlocatie van de meegeven robot 
	 * 
	 * @param robotId
	 * @return
	 */
	public abstract Location getStartLocation(int robotId);

	/**
	 * Geeft het maximale aantal producten op de fork terug
	 * 
	 * @return
	 */
	public abstract int getMaxLoad();
}
