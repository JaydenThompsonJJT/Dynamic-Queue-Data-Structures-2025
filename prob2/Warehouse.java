package prob2;

import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Warehouse {
	Set<Product> s = new HashSet<>();
	Set<Product> perishable = new HashSet<>();
	Set<Product> nonPerishable = new HashSet<>();
	ArrayList<NonPerishable> notPerishable = new ArrayList<>();
	PriorityQueue<Chips> chips = new PriorityQueue<>();
	PriorityQueue<Rice> rice = new PriorityQueue<>();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	
	
	public static void buyInventory() {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Type a product and the quantity you want to purchase in the format(Pants) press enter then the quantity(12)");
		String item = scnr.nextLine();
		System.out.println("Type the quantity you want to purchase");
		String quant = scnr.nextLine();
		int amount;
			try {
			amount = Integer.parseInt(quant);
		} 
		catch (NumberFormatException e) {
		    System.out.println("Invalid integer input");
		}
		unload(quant,amount);
		stock();
	}

	public static void unload(String product, int units) {
		String thing = product;
		int amount = units;
		int skuForNotPerishable = 0;
		int skuForChips = 0;
		int skuForRice = 0;
		for(int i=0; i<amount;i++) {
			if(item == "notPerishable") {
				skuForNotPerishable++;
				NonPerishable notPerishable = new NonPerishable(skuForNotPerishable);
				s.add(notPerishable);
			}
			else if(item == "Chips") {
				skuForChips ++;
				Chips chips = new Chips(skuForChips);
				s.add(chips);
				
			}
			else{
				skuForRice ++;
				Rice rice = new Rice(skuForRice);
				s.add(rice);
				
			}
	}
	
	public static void stock() {
		canSpoil();
		organize();
	}
	
	public static void canSpoil () {
		
		Iterator iter = new Iterator(s);
		while(iter.hasNext()) {
			Product p = iter.next();
			if(p.isPerishable()) {
				perishable.add(p);
			}
			else {
				nonPerishable.add(p);
			}
		}
		
		
		
	}
	
	public static void organize() {
		Iterator iter = new Iterator(perishable);
		while(iter.hasNext()) {
			Product p = iter.next();
			
			notPerishable.add(p);
			
		}
		Iterator iter2 = new Iterator(nonPerishable);
		while(iter2.hasNext()) {
			Product p = iter2.next();
			if(p.instanceOf(Chips)) {
				chips.add(p);
			}
			else {
				rice.add(p);
			}
		}
	}
	

}
