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

	private ExecutionManager executionManager;

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

		waitForArduinoReady();

		int color = inputData.get(1);
	}

	@Override
	public void bringToBinPacker(int robotId) {
		sendByte(DELIVER_PRODUCT);

		waitForArduinoReady();
	}

	@Override
	public void moveToStart(int robotId) {
		sendByte(DONE);

		waitForArduinoReady();

		close();
	}

	@Override
	public Location getStartLocation(int r) {
		return new Location(0, 0);
	}

	@Override
	public Integer getNumberOfRobots() {
		return 1;
	}

}