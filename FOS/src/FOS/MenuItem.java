package FOS;
import java.util.*;

public class MenuItem {
	private String food;
	private double price;
	private List<String> regularIngredients;
	private List<String> extras;
	
	public MenuItem(String food, double price, List<String> regular, List<String> extras) {
		this.food = food;
		this.price = price;
		this.regularIngredients = new ArrayList<>(regular);
		this.extras = new ArrayList<>(extras);
	}
	
	public String getFood() {
		return food;
	}
	
	public double getPrice() {
		return price;
	}
	
	public List<String> getRegularIngredients(){
		return regularIngredients;
	}
	
	public List<String> getExtras(){
		return extras;
	}
	
	@Override
	public String toString() {
		return food + " $" + price + " - Ingredients: " + regularIngredients;
	}
}
