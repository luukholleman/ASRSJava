package asrsController;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

import order.Location;

public class WarehouseArduino extends Arduino implements Warehouse{
	
	private static final int RETRIEVE = (byte)1;
	
	public WarehouseArduino(CommPortIdentifier port){
		super(port);
	}

	@Override
	public void retrieveProduct(Location location, int robotId) {
		// open de connectie
		open();
		
		// geef het commando retrieve, parameters zijn de x en y locatie
		Byte[] bytes = {RETRIEVE, (byte)location.x, (byte)location.y};
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