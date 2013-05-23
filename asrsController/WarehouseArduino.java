package asrsController;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

import order.Location;
import order.Order;
import order.Product;

public class WarehouseArduino extends Arduino implements Warehouse{
	
	private ExecutionManager executionManager;
	
	private static final int RETRIEVE = (byte)1;
	
	public WarehouseArduino(CommPortIdentifier port){
		super(port);
	}
	
	public void setExecutionManager(ExecutionManager executionManager) {
		this.executionManager = executionManager;
	}
	
	public void start() {
		open();
		
		Order order = executionManager.getOrder();
		
		for(Product p : order.getProducts()) {
			System.out.println("Product ophalen: " + p.getId() + p.getDescription());
			retrieveProduct(p.getLocation(), 0);
		}
		
		close();
	}

	@Override
	public void retrieveProduct(Location location, int robotId) {
		
		System.out.println("Retrieve product");
		
		// geef het commando retrieve, parameters zijn de x en y locatie
		Byte[] bytes = {RETRIEVE, (byte)location.x, (byte)location.y};
		
		sendBytes(bytes);
	}

	@Override
	public void bringToBinPacker(int robotId) {
		open();
		
		sendByte((byte)2);
		
		close();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToStart(int robotId) {
		open();
		
		sendByte((byte)3);
		
		close();
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