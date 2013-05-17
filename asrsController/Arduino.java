package asrsController;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
 
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Arduino implements SerialPortEventListener{
	
	SerialPort serialPort;
	
	// Buffered input stream van de poort
	private InputStream input;
	// De output stream naar de poort
	private OutputStream output;
	// Aantal milliseconden wachten tot de poort opent
	private static final int TIME_OUT = 2000;
	// Standaard bits per second voor de COM poort.
	private static final int DATA_RATE = 9600;
	
	public Arduino (CommPortIdentifier port){
		if (port == null) {
            System.out.println("Kan de COM poort niet vinden");
            return;
        }else{
            System.out.println("COM poort gevonden");
        }

        try {
            // Open seri�le poort en gebruik de class naam voor de appNaam
            serialPort = (SerialPort) port.open(this.getClass().getName(),TIME_OUT);
            
            // Poort parameters aanwijzen
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // Open de streams
            input = serialPort.getInputStream();
            output = serialPort.getOutputStream();

            // Toevoegen event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
	}
	
	
	// Meerdere bytes verzenden
	public void sendBytes(Byte[] bytes){
		try{
			for(Byte b : bytes){
	            output.write(b);
	            output.flush(); // Ik weet niet of deze hier moet staan of onder de for-loop)
	        } 
		} catch(Exception e){
            System.err.println(e.toString());
        }
	}
	
	
	// ��n byte verzenden
	public void sendByte(Byte b){
		try{
            output.write(b);
            output.flush();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
	}
	
	// Gebruik deze methode om de poort weer af te sluiten als je klaar bent
	public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }
	
	// Deze methode word aangeroepen als er seri�le data ontvangen is
	public void serialEvent(SerialPortEvent oEvent){
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
	        	int myByte=input.read();
	            int value = myByte & 0xff; //byte to int conversion:0...127,-127...0 -> 0...255
	            if(value>=0 && value<256){ //make sure everything is OK
	            	System.out.println(value);
	                sendByte((byte)myByte);
	            }
	        } catch (Exception e) {
	        	System.err.println(e.toString());
	        }
		}
	}
	
}