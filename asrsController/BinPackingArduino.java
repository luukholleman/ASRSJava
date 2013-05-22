package asrsController;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

import order.Product;

public class BinPackingArduino extends Arduino implements BinPacking{
	private ExecutionManager executionManager;
	private int bins;
	private CommPortIdentifier port;
	
	public BinPackingArduino (CommPortIdentifier port){
		super(port);
	}
	
	public void sentToBin(Byte binNummer){
		
	}
	
	public void packProduct(Byte binNummer, Product product){
		
	}
}