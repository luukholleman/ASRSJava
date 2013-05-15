package asrsController;

import order.Location;

public interface Warehouse {
	public abstract void retrieveProduct(Location location, int robotId);
	
	public abstract void bringToBinPacker(int robotId);
	
	public abstract void moveToStart(int robotId);
	
	public abstract Integer getNumberOfRobots();

	public abstract Location getStartLocation(int r);

}
