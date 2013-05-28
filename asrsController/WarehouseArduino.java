/**
 * @author Luuk
 */
package asrsController;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

import order.Location;
import order.Order;
import order.Product;

public class WarehouseArduino extends Arduino implements Warehouse {

	public static final byte PICKUP_PRODUCT = 1;
	public static final byte DELIVER_PRODUCT = 2;
	public static final byte DONE = 3;

	private ExecutionManager executionManager;

	private byte executingCommand;

	public WarehouseArduino(CommPortIdentifier port) {
		super(port);

		open();
	}

	public void setExecutionManager(ExecutionManager executionManager) {
		this.executionManager = executionManager;
	}

	@Override
	public void retrieveProduct(Location location, int robotId) {

		System.out.println("Retrieve product");

		// geef het commando retrieve, parameters zijn de x en y locatie
		Byte[] bytes = { PICKUP_PRODUCT, (byte) location.x, (byte) location.y };

		sendBytes(bytes);

		executingCommand = PICKUP_PRODUCT;
	}

	@Override
	public void bringToBinPacker(int robotId) {
		System.out.println("Bring product");
		sendByte(DELIVER_PRODUCT);
		
		executingCommand = DELIVER_PRODUCT;
	}

	@Override
	public void moveToStart(int robotId) {
		sendByte(DONE);

		executingCommand = DONE;
		
		//Sluit de connectie...
		close();
	}

	@Override
	public Location getStartLocation(int robotId) {
		return new Location(0, 0);
	}

	@Override
	public Integer getNumberOfRobots() {
		return 1;
	}

	@Override
	public void receivedData() {
		System.out.println("Response!");
		switch (executingCommand) {
		case PICKUP_PRODUCT:
			System.out.println("Pickup's response...");
			if(getInputBuffer().size() == 2)
			{
				System.out.println("Pickup has two bytes in buffer...");
				//Vertel EM dat we een product hebben
				executionManager.pickedUpProduct(0, (byte)((int)getInputBuffer().get(0)));
			}
			break;
		case DELIVER_PRODUCT:
			System.out.println("Deliver's response...");
			executionManager.deliveredProduct(0);
			break;
		}
	}
}