package asrsController;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

import order.Product;

/**
 *  De bin packing arduino klasse om de producten naar bins te versturen
 *  
 * @author Mike
 *
 */
public class BinPackingArduino extends Arduino implements BinPacking{
	
	/**
	 * 
	 * @param port Poort om mee te verbinden
	 */
	public BinPackingArduino (CommPortIdentifier port){
		super(port);
		
		//opent seriele communicatie
		open();
	}
	
	@Override
	public void packProduct(Byte binNummer, Product product){
		//stuurt het binnummer naar de Arduino
		sendByte(binNummer);
	}
}