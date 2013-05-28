package asrsController;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

import order.Product;

public class BinPackingArduino extends Arduino implements BinPacking{
	public BinPackingArduino (CommPortIdentifier port){
		super(port);
		
		//opent seriele communicatie
		open();
	}
	
	public void packProduct(Byte binNummer, Product product){
		//stuurt het binnummer naar de Arduino
		sendByte((byte)(binNummer + 1));
		
		//sluit de seriele communicatie
		//close();
	}
}