package asrsController;

import order.Location;

public interface Warehouse {
	public abstract void retrieveProduct(Location location, int robotId);
	
	public abstract void bringToBinPacker(int robotId);
	
	public abstract void moveToStart(int robotId);
	
	public abstract int getNumberOfRobots();

	public abstract Location getStartLocation(int r);

	/**
	 * Geeft het maximale aantal producten op de fork terug
	 * @return
	 */
	public abstract int getMaxLoad();
}
