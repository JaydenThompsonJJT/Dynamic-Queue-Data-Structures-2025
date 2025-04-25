package prob2;

public class Item {
	private int id;
	private String name;
	private double price;

	public Item(int id, String name) {
		this.id = id;
		this.name = name;
		this.price = 0.0;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isPerishable() {
		return false;
	}
}
