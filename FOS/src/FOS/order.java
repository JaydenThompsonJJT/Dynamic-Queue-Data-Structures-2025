package FOS;
import java.util.*;

public class order {
	private static int idCounter = 1;
	private int orderId;
	private String customerName;
	private MenuItem item;
	private List<String> removedIngredients;
	private List<String> addedExtras;
	private boolean isCombo;
	private double finalPrice;
	private String status;
	
	public order(String customerName, MenuItem item, List<String> removedIngredients, List<String> addedExtras, boolean isCombo) {
		this.orderId = idCounter++;
		this.customerName = customerName;
		this.item = item;
		this.removedIngredients = new ArrayList<>(removedIngredients);
		this.addedExtras = new ArrayList<>(addedExtras);
		this.isCombo = isCombo;
		this.status = "Pending";
		
		finalPrice = item.getPrice() + (addedExtras.size() * .50) + (isCombo ? 3.00 : 0.00);
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public MenuItem getItem() {
		return item;
	}
	
	public List<String> getRemovedIngredients() {
		return removedIngredients;
	}
	
	public List<String> getAddedExtras() {
		return addedExtras;
	}
	
	public boolean getIsCombo() {
		return  isCombo;
	}
	
	public double getFinalPrice() {
		return finalPrice;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Order #" + orderId + " | " + customerName + " Ordered " + (isCombo ? "A Combo of " : "") + item.getFood() + "\nRemoved: " + removedIngredients + "\nAdded: " + addedExtras + "\nTotal Price: $" + finalPrice + "\nStatus: " + status;
	}

}
