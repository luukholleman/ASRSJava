package asrsController;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.TooManyListenersException;

public abstract class Arduino implements SerialPortEventListener {

	SerialPort serialPort;

	// Buffered input stream van de poort
	private InputStream input;
	// De output stream naar de poort
	private OutputStream output;

	// Aantal milliseconden wachten tot de poort opent
	private static final int TIME_OUT = 2000;

	// Standaard bits per second voor de COM poort.
	private static final int DATA_RATE = 9600;

	private CommPortIdentifier port;

	/**
	 * Buffer met inkomende data
	 */
	private ArrayList<Integer> inputBuffer = new ArrayList<Integer>();

	/**
	 * ctor
	 * 
	 * @param port
	 */
	public Arduino(CommPortIdentifier port) {
		System.out.println("Created arduino on " + port.getName());

		this.port = port;
	}

	/**
	 * Open de connectie met de arduino
	 * 
	 * @return void
	 */
	public void open() {
		System.out.println("Serial Open");

		try {
			// Open seriele poort en gebruik de class naam voor de appNaam
			serialPort = (SerialPort) port.open(this.getClass().getName(),
					TIME_OUT);

			// Poort parameters aanwijzen
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// Open de streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			// Toevoegen event listeners en zorg dat het event wordt getriggerd
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		//Wacht even zodat de arduino op kan starten
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Woke up");
	}

	/**
	 * Verzend meerdere bytes naar de arduino
	 * 
	 * @param bytes
	 */
	public void sendBytes(Byte[] bytes) {
		for (Byte b : bytes)
			sendByte(b);
	}

	/**
	 * Verzend een byte naar de arduino
	 * 
	 * @param b
	 */
	public void sendByte(Byte b) {
		inputBuffer.clear();

		System.out.println("Serial sendbyte");
		try {
			System.out.println("Output: " + b);
			// schrijf de byte naar arduino
			output.write(b);
			output.flush();

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * Sluit de serial port
	 */
	public synchronized void close() {
		System.out.println("Serial close");
		if (serialPort != null) {
			// sluit hem
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Dit event wordt getriggerd als er data ontvangen is
	 */
	@Override
	public void serialEvent(SerialPortEvent oEvent) {

		if(SerialPortEvent.DATA_AVAILABLE != oEvent.getEventType())
			return;
		
		int data;

		try {
			while ((data = input.read()) > -1) {
				System.out.println("Input: " + data);

				inputBuffer.add(data);

				receivedData();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wordt aangeroepen als er data binnenkomt
	 */
	public void receivedData() {
		// Overwritable
	}

	/**
	 * Verkrijg een lijst met inkomende data
	 * 
	 * @return
	 */
	public ArrayList<Integer> getInputBuffer() {
		return inputBuffer;
	}

}