public class Customer {
	private int id;
	private String name;

	public Customer(int id, String name) {
		this.setId(id);
		this.setName(name);
	}

	private int getId() {
		return id;
	}

	private String getName() {
		return name;
	}
}
