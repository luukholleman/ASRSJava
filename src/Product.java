public class Product {
	private int id;
	private String description;
	private Float price;
	private String status;
	private int size;
	private Location location;

	public Product(int id, String description, Float price, int size,
			Location location) {
		this.id = id;
		this.description = description;
		this.price = price;
		this.status = "";
		this.size = size;
		this.location = location;
	}
}
