package order;

import asrs.DBHandler;
import asrs.ProductNotFoundException;

public class Product {
	private int id;
	private String description;
	private float price;
	private String status;
	private int size;
	private Location location;

	public Product(int id, String description, float price) throws ProductNotFoundException {
		this.id = id;
		this.description = description;
		this.price = price;
		this.status = "";
		
		DBHandler.getProductDatabaseInfo(this);
	}
	
	public Product(Location location, int id){
		this.location = location;
		this.id = id;
	}
	
	public Product(int size, int id){
		this.size = size;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public float getPrice() {
		return price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
