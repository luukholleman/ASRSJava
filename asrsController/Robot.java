package asrsController;

import order.Location;

public class Robot {
	private Location currentLocation;
	private Location goalLocation;
	private int load;
	
	public Robot(){
		setCurrentLocation(new Location(0,0));
		setGoalLocation(new Location(0,0));
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	public Location getGoalLocation() {
		return goalLocation;
	}

	public void setGoalLocation(Location goalLocation) {
		this.goalLocation = goalLocation;
	}

	private int getLoad() {
		return load;
	}

	private void setLoad(int load) {
		this.load = load;
	}
}
