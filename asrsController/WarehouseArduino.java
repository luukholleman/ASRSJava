package asrsController;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

import order.Location;

public class WarehouseArduino extends Arduino implements Warehouse{
	
	public Arduino (CommPortIdentifier port){
		
	}

	@Override
	public void retrieveProduct(Location location, int robotId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bringToBinPacker(int robotId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToStart(int robotId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Location getStartLocation(int r) {
		return new Location(0,0);
	}

	@Override
	public Integer getNumberOfRobots() {
		return 1;
	}

}