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

	public static final byte PICKUP_PRODUCT = 1;
	public static final byte DELIVER_PRODUCT = 2;
	public static final byte DONE = 3;
	public static final byte IS_READY = 4;
	
	/**
	 * Houdt bij of de arduino klaar is met de opdracht
	 * wordt op false gezet als je een opdracht verstuurd
	 * true wanneer de arduino aangeeft dat hij klaar is
	 */
	boolean arduinoReady;
	
	private CommPortIdentifier port;
	
	/**
	 * De ontvangen data, wordt automatisch geleegd bij versturen van een nieuwe opdracht
	 */
	public ArrayList<Integer> inputData = new ArrayList<Integer>();
	
	/**
	 * ctor
	 * 
	 * @param port
	 */
	public Arduino (CommPortIdentifier port){		
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
            // Open seriële poort en gebruik de class naam voor de appNaam
            serialPort = (SerialPort) port.open(this.getClass().getName(), TIME_OUT);
            
            // Poort parameters aanwijzen
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // Open de streams
            input = serialPort.getInputStream();
            output = serialPort.getOutputStream();

            // Toevoegen event listeners en zorg dat het event wordt getriggerd
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            
        } catch (Exception e) {
			e.printStackTrace();
        }
        
        // test elke halve seconde of de arduino aan staat. De loop wordt automatisch doorbroken als dit het geval is
        while(arduinoReady == false) {
            sendByte(IS_READY);
            
            try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	/**
	 * Laat de thread 'slapen' tot de arduino klaar is
	 */
	public void waitForArduinoReady() {
		while(arduinoReady == false);
	}
	
	
	/**
	 * Meerdere bytes verzenden
	 * 
	 * @param bytes
	 */
	public void sendBytes(Byte[] bytes){
		inputData.clear();
		
		System.out.println("Serial sendbytes");
		try{
			// schrijf de bytes naar arduino
			for(Byte b : bytes){
				System.out.println("Sending: " + b);
				
	            output.write(b);
	            output.flush();
	        } 
			
			// omdat we weer een opdracht hebben versturen is de arduino niet klaar
			arduinoReady = false;
			
		} catch(IOException e){
            System.err.println(e.toString());
        }
	}
	
	
	/**
	 * 1 byte verzenden
	 * 
	 * @param b
	 */
	public void sendByte(Byte b){
		inputData.clear();
		
		arduinoReady = false;
		System.out.println("Serial sendbyte");
		try{
			System.out.println("Sending: " + b);
			// schrijf de byte naar arduino
            output.write(b);
            output.flush();
			
			// omdat we weer een opdracht hebben versturen is de arduino niet klaar
			arduinoReady = false;
			
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
	public void serialEvent(SerialPortEvent oEvent){
		
		int data;
		byte[] buffer = new byte[1024];
        
        try
        {
            int len = 0;
            while ( ( data = input.read()) > -1 )
            {
            	System.out.println("Data input: " + Character.getNumericValue(data));
                if ( data == '\n' ) {
                    break;
                }
                
                inputData.add(Character.getNumericValue(data));
                
//                buffer[len++] = (byte) data;
            }
            
// arduino is weer klaar
arduinoReady = true;
    		
    		// input wegschrijven naar console
            System.out.print(new String(buffer,0,len));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }  
	}
	
	
}