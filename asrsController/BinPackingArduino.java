package asrsController;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

public class BinPackingArduino extends Arduino implements BinPacking{
	
	public BinPackingArduino (CommPortIdentifier port){
		super(port);
	}
}