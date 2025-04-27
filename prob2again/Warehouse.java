package prob2again;

import java.util.*;

public class Warehouse {
	private Set<Item> items;
	private Set<Item> perishable;
	private Set<Item> nonPerishable;
	private ArrayList<NonPerishableItem> notPerishable;
	private PriorityQueue<Item> chips;
	private PriorityQueue<Item> rice;
	private int nextId = 1;
	
	public Warehouse() {
		items = new HashSet<>();
		perishable = new HashSet<>();
		nonPerishable = new HashSet<>();
		notPerishable = new ArrayList<>();
		chips = new PriorityQueue<>(new DateComparator());
		rice = new PriorityQueue<>(new DateComparator());
		nextId = 1;
	}
	
	public static void main(String[] args) {
		Warehouse w = new Warehouse();
	    Scanner scnr = new Scanner(System.in);

	    boolean running = true;
	    while(running) {
	    	 System.out.println("\n--- Warehouse Inventory System ---");
	         System.out.println("1. Buy Inventory");
	         System.out.println("2. View Current Inventory");
	         System.out.println("3. Process Expired Items");
	         System.out.println("4. Ship Items");
	         System.out.println("5. Exit");
	         System.out.println("Enter your choice (1-5): ");
	        
	        String input = scnr.nextLine();
	        
	        switch(input) {
	            case "1":
	                w.buyInventory(scnr);
	                break;
	            case "2":
	                w.viewInventory();
	                break;
	            case "3":
	                w.processExpiredItems();
	                break;
	            case "4":
	                w.shipItems(scnr);
	                break;
	            case "5":
	                System.out.println("Thank you for using our Warehouse inventory system.");
	                running = false;
	                break;
	            default:
	                System.out.println("Invalid choice. Please enter 1-5.");
	        }
	    }
	    scnr.close();
	}

	public void buyInventory(Scanner scnr) {
		System.out.println("Enter the name of the item you want to purchase (Chips, Rice, or a non-perishable good (ex.: iPhone)):");
		String item = scnr.nextLine();
		System.out.println("Type the quantity of the item you want to purchase:");
		int quantity = 0;
		try {
			quantity = Integer.parseInt(scnr.nextLine());
		}
		catch(NumberFormatException e) {
			System.out.println("Invalid input. Please enter a valid number.");
			scnr.close();
			return;
		}
		
		unload(item, quantity);
		stock();
	}
	
	public void viewInventory() {
	    System.out.println("--- Current Inventory ---");
	    
	    List<PerishableItem> sortedPerishables = new ArrayList<>();
	    for(Item item : chips) {
	        if(item instanceof PerishableItem) {
	            sortedPerishables.add((PerishableItem)item);
	        }
	    }
	    for(Item item : rice) {
	        if(item instanceof PerishableItem) {
	            sortedPerishables.add((PerishableItem)item);
	        }
	    }

	    sortedPerishables.sort(Comparator.comparingInt(PerishableItem::getExpireInDays));

	    System.out.println("Perishable Items:");
	    for(PerishableItem perishableItem : sortedPerishables) {
	        System.out.println("- " + perishableItem.getName() + " (ID: " + perishableItem.getId() + ") | Expiration: " + perishableItem.getExpireInDays() + " days");
	    }

	    System.out.println("Non-Perishable Items:");
	    for(Item item : nonPerishable) {
	        System.out.println("- " + item.getName() + " (ID: " + item.getId() + ")");
	    }

	    int totalItems = chips.size() + rice.size() + nonPerishable.size();
	    System.out.println("--------------------------");
	    System.out.println("Total items in inventory: " + totalItems);
	}

	
	public void processExpiredItems() {
		Iterator<Item> chipIterator = chips.iterator();
	    while(chipIterator.hasNext()) {
	        Item chip = chipIterator.next();
	        if(chip instanceof PerishableItem && ((PerishableItem)chip).isExpired()) {
	            System.out.println("Expired item: " + chip.getName() + " (ID: " + chip.getId() + ")");
	            chipIterator.remove();
	        }
	    }

	    Iterator<Item> riceIterator = rice.iterator();
	    while(riceIterator.hasNext()) {
	        Item rice = riceIterator.next();
	        if(rice instanceof PerishableItem && ((PerishableItem)rice).isExpired()) {
	            System.out.println("Expired item: " + rice.getName() + " (ID: " + rice.getId() + ")");
	            riceIterator.remove();
	        }
	    }
	}

	public void unload(String itemName, int quantity) {
		for(int i = 0; i < quantity; i++) {
			if(itemName.equalsIgnoreCase("Chips")) {
                Chips chip = new Chips(nextId++);
                items.add(chip);
            }
			else if(itemName.equalsIgnoreCase("Rice")) {
                Rice rice = new Rice(nextId++);
                items.add(rice);
            }
			else {
                NonPerishableItem npItem = new NonPerishableItem(nextId++, itemName);
                items.add(npItem);
            }
	    }
	}
	
	public void stock() {
		canSpoil();
		organize();
	}
	
	public void canSpoil() {
		perishable.clear();
		nonPerishable.clear();
		
		for(Item i : items) {
			if(i.isPerishable()) {
				perishable.add(i);
			} 
			else {
				nonPerishable.add(i);
			}
		}
	}
	
	public void organize() {
		notPerishable.clear();
		chips.clear();
		rice.clear();
		
		for(Item item : nonPerishable) {
			if(item instanceof NonPerishableItem) {
				notPerishable.add((NonPerishableItem)item);
			}
		}
		for(Item item : perishable) {
			if(item instanceof Chips) {
				chips.add((Chips)item);
			} 
			else if(item instanceof Rice) {
				rice.add((Rice)item);
			}
		}
	}
	
	public void shipItems(Scanner scnr) {
	    System.out.println("Enter the name of the item to ship (Chips, Rice, etc.):");
	    String itemName = scnr.nextLine().toLowerCase();
	    
	    System.out.println("Enter the quantity of " + itemName + " to ship:");
	    int quantityToShip = 0;
	    try {
	        quantityToShip = Integer.parseInt(scnr.nextLine());
	    } 
	    catch (NumberFormatException e) {
	        System.out.println("Invalid quantity entered. Please enter a valid number.");
	        return;
	    }

	    boolean itemsShipped = false;

	    if(itemName.equals("chips")) {
	        int shippedCount = 0;
	        Iterator<Item> itemIterator = chips.iterator();

	        while(itemIterator.hasNext() && shippedCount < quantityToShip) {
	            Item item = itemIterator.next();
	            System.out.println("Shipping out: " + item.getName() + " (ID: " + item.getId() + ")");
	            itemIterator.remove();
	            shippedCount++; 
	        }


	        if(shippedCount > 0) {
	            System.out.println("Successfully shipped " + shippedCount + " " + itemName + "(s).");
	            itemsShipped = true;
	        } 
	        else {
	            System.out.println("Not enough " + itemName + " in stock to ship.");
	        }
	    }
	    else if(itemName.equals("rice")) {
	        int shippedCount = 0;
	        Iterator<Item> itemIterator = rice.iterator();

	        while(itemIterator.hasNext() && shippedCount < quantityToShip) {
	            Item item = itemIterator.next();
	            System.out.println("Shipping out: " + item.getName() + " (ID: " + item.getId() + ")");
	            itemIterator.remove();
	            shippedCount++;
	        }

	        if(shippedCount > 0) {
	            System.out.println("Successfully shipped " + shippedCount + " " + itemName + "(s).");
	            itemsShipped = true;
	        } 
	        else {
	            System.out.println("Not enough " + itemName + " in stock to ship.");
	        }
	    }

	    else {
	        int shippedCount = 0;

	        for(Iterator<Item> nonPerishableIterator = nonPerishable.iterator(); nonPerishableIterator.hasNext() && shippedCount < quantityToShip; ) {
	            Item item = nonPerishableIterator.next();
	            if(item.getName().equalsIgnoreCase(itemName)) {
	                System.out.println("Shipping out: " + item.getName() + " (ID: " + item.getId() + ")");
	                nonPerishableIterator.remove();
	                shippedCount++; 
	            }
	        }

	        if(shippedCount > 0) {
	            System.out.println("Successfully shipped " + shippedCount + " " + itemName + "(s).");
	            itemsShipped = true;
	        } 
	        else {
	            System.out.println("Not enough " + itemName + " in stock to ship.");
	        }
	    }
	    
	    if(!itemsShipped) {
	        System.out.println("No such item found in inventory.");
	    }
	}

}
