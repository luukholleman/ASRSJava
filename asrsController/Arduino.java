package asrsController;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
 
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

public class Arduino implements SerialPortEventListener{
	
	public Arduino (String port){
		
	}
	
	public void sendBytes(Byte[] bytes){
		
	}
	
	public void sendByte(Byte b){
		
	}
	
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