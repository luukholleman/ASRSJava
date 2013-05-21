package asrsController;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

public class BinPackingArduino extends Arduino implements BinPacking{
	private ExecutionManager executionManager;
	private int bins;
	private CommPortIdentifier port;
	
	public BinPackingArduino (ExecutionManager executionManager, int bins, CommPortIdentifier port){
		super(port);
		this.executionManager = executionManager;
		this.bins = bins;
	}
	
}