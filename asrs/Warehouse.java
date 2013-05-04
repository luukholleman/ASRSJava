package asrs;

public interface Warehouse {
	public abstract void retrieveProduct(Location location, Integer robotId);
	
	public abstract void bringToBinPacker();
	
	public abstract void moveToStart();
	
	public abstract Integer getRobots();
}
