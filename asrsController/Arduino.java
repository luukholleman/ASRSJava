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
	
	public Arduino (CommPortIdentifier port){
		if (port == null) {
            System.out.println("Kan de COM poort niet vinden");
            return;
        }else{
            System.out.println("COM poort gevonden");
        }
		
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
        } catch (PortInUseException e) {
            System.err.println(e.toString());
        } catch (UnsupportedCommOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// Meerdere bytes verzenden
	public void sendBytes(Byte[] bytes){
		System.out.println("Serial sendbytes");
		try{
			for(Byte b : bytes){
	            output.write(b);
	            output.flush();
	        } 
		} catch(IOException e){
            System.err.println(e.toString());
        }
	}
	
	
	// Één byte verzenden
	public void sendByte(Byte b){
		System.out.println("Serial sendbyte");
		try{
            output.write(b);
            output.flush();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
	}
	
	// Gebruik deze methode om de poort weer af te sluiten als je klaar bent
	public synchronized void close() {
		System.out.println("Serial close");
        if (serialPort != null) {
//            serialPort.removeEventListener();
            //serialPort.close();
        }
    }
		
	// Deze methode word aangeroepen als er seriële data ontvangen is
	@Override
	public void serialEvent(SerialPortEvent oEvent){
		int data;
		byte[] buffer = new byte[1024];
        
        try
        {
            int len = 0;
            while ( ( data = input.read()) > -1 )
            {
                if ( data == '\n' ) {
                    break;
                }
                buffer[len++] = (byte) data;
            }
            System.out.print(new String(buffer,0,len));
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            System.exit(-1);
        }  
        
//        
//		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
//			try {
//	        	int myByte=input.read();
//	            int value = myByte & 0xff; //byte to int conversion:0...127,-127...0 -> 0...255
//	            if(value>=0 && value<256){ //make sure everything is OK
//	            	System.out.println("Arduino reageerd met: " + value);
//	            }
//	        } catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	
	
}