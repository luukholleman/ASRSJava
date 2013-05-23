package asrsController;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

import order.Product;

public class BinPackingArduino extends Arduino implements BinPacking{
	private CommPortIdentifier port;
	
	public BinPackingArduino (CommPortIdentifier port){
		super(port);
	}
	
	public void packProduct(Byte binNummer, Product product){
		//opent seriële communicatie
		open();
		//stuurt het binnummer naar de Arduino
		sendByte(binNummer);
		//sluit de seriële communicatie
		close();
	}
}