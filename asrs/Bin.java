package asrs;

public class Bin {
	private int size;
	private int filled;
	
	public Bin(int size, int filled){
		this.size = size;
		this.filled = filled;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getFilled() {
		return filled;
	}
	public void setFilled(int filled) {
		this.filled = filled;
	}
}
