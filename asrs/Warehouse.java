package asrs;

public interface Warehouse {
	public void retrieveProduct(Location location, Integer robotId);
	
	public void bringToBinPacker();
	
	public void moveToStart();
	
	public Integer getRobots();
}
