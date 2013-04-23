public class Location {
	public int x;
	public int y;

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Float getDistanceTo(Location toLocation)
	{
		return (float)Math.sqrt(Math.pow(Math.abs(toLocation.x-x),2) + Math.pow(Math.abs(toLocation.y-y),2));
	}
}
