package codebyrj;

import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Warehouse {
	Set<Product> s = new HashSet<>();
	Set<Product> parishable = new HashSet<>();
	Set<Product> nonParishable = new HashSet<>();
	ArrayList<NonParishable> notParishable = new ArrayList<>();
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
		int amount = units
		int skuForPants = 0;
		int skuForIphone16 = 0;
		int skuForChips = 0;
		int skuForRice = 0;
		for(int i=0; i<amount;i++) {
			if(item == "Pants") {
				skuForPants ++;
				Pants pants = new Pants(skuForPants);
				s.add(pants);
			}
			else if(item == "Iphone16") {
				skuForIphone16 ++;
				Iphone16 iphone16 = new Iphone16(skuForIphone16);
				s.add(iphone16);
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
	}
	
	public static void stock() {
		canSpoil();
		organize();
	}
	
	public static void canSpoil () {
		
		Iterator iter = new Iterator(s);
		while(iter.hasNext()) {
			Product p = iter.next();
			if(p.isParishable()) {
				parishable.add(p);
			}
			else {
				nonParishable.add(p);
			}
		}
		
		
		
	}
	
	public static void organize() {
		Iterator iter = new Iterator(parishable);
		while(iter.hasNext()) {
			Product p = iter.next();
			
			notParishable.add(p);
			
		}
		Iterator iter2 = new Iterator(nonParishable);
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
