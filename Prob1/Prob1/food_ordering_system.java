package Prob1;
import java.util.*;

public class food_ordering_system {
		public static void main(String[] args) {
			Scanner scnr = new Scanner(System.in);
			dynamic_Queue<order> orderQueue = new dynamic_Queue<>(order.class);		// Creates the Dynamic Queue for the orders
			
			List<MenuItem> menu = new ArrayList<>();		//Creates the menu list
			menu.add(new MenuItem("Burger", 5.99, Arrays.asList("Bun", "Beef Patty", "Lettue", "Tomato", "Cheese"), Arrays.asList("Bacon", "Extra Cheese", "Pickles")));
			menu.add(new MenuItem("Pizza", 8.99, Arrays.asList("Tomato Sauce", "Cheese", "Pepperoni"), Arrays.asList("Mushrooms", "Pineapples", "Bacon")));
			menu.add(new MenuItem("Salad", 6.99, Arrays.asList("Lettuce", "Tomatoes", "Cheese", "Cucumbers", "Olives"), Arrays.asList("Chicken", "Ranch", "Croutons")));
			
			
			while (true) {		// infinite loop until its exited
				System.out.println("--- Restaurant Ordering System ---");
				System.out.println("1.  Place Order");
				System.out.println("2.  Repeat Order");
				System.out.println("3.  View Next Order");
				System.out.println("4.  Process Order");
				System.out.println("5.  View Queue Size");
				System.out.println("6.  Exit\n");
				System.out.print("Choose an option: ");
				
				int option = scnr.nextInt();		//The users 
				scnr.nextLine();
				
				switch (option) {
				case 1:
					System.out.print("Customer Name: ");
					String name = scnr.nextLine();
					System.out.println("  -- Menu --  ");
					for (int i = 0; i < menu.size(); i++) {
						System.out.println((i+1) + ". " + menu.get(i));
					}
					
					System.out.println("Select an item by number: ");
					int menuChoice = scnr.nextInt() - 1;
					scnr.nextLine();
					if (menuChoice < 0 || menuChoice >= menu.size()) {
						System.out.println("Invalid Choice: ");
						break;
					}
					MenuItem selectedItem = menu.get(menuChoice);
					
					// Order Customization
					List<String> removedIngredients = new ArrayList<>();
					System.out.println("Would you like to remove ingredients? (yes/no): ");
					if (scnr.nextLine().equalsIgnoreCase("yes")) {
						System.out.println("Available to remove: " + selectedItem.getRegularIngredients());
						System.out.print("Enter ingredients to remove (seperate by commas)");
						removedIngredients = Arrays.asList(scnr.nextLine().split(","));
						
					}
					
					List<String> addedExtras = new ArrayList<>();
					System.out.println("Would you like to add any extras? (yes/no): ");
					if (scnr.nextLine().equalsIgnoreCase("yes")) {
						System.out.println("Available extras: " + selectedItem.getExtras());
						System.out.print("Enter ingredients to remove (seperate by commas)");
						addedExtras = Arrays.asList(scnr.nextLine().split(","));
						
					}
					
					System.out.print("Would you like to make this a combo meal? (yes/no): ");
					boolean isCombo = scnr.nextLine().equalsIgnoreCase("yes");
					
					System.out.println();
					order newOrder = new order(name, selectedItem, removedIngredients, addedExtras, isCombo);
					orderQueue.push(newOrder);
					System.out.println("Order placed: " + newOrder);					
					break;
					
				case 2:
					if (orderQueue.isEmpty()) {
						System.out.println("There are no orders that can be reordered");
					}
					else {
                        // Fetch the last order
                        order lastOrder = orderQueue.front();

                        // Recreate the order with the same details (customizations remain the same)
                        String nameForReorder = lastOrder.getCustomerName();
                        MenuItem selectedItemForReorder = lastOrder.getItem();
                        List<String> removedIngredientsForReorder = new ArrayList<>(lastOrder.getRemovedIngredients());
                        List<String> addedExtrasForReorder = new ArrayList<>(lastOrder.getAddedExtras());
                        boolean isComboForReorder = lastOrder.getIsCombo();

                        // Create a new order object for reorder
                        order newReorder = new order(nameForReorder, selectedItemForReorder, removedIngredientsForReorder, addedExtrasForReorder, isComboForReorder);
                        
                        // Add the new reorder to the queue
                        orderQueue.push(newReorder);
                        System.out.println("Reordered successfully: " + newReorder);
                    }
					break;
				case 3:
					if (orderQueue.isEmpty()) {
						System.out.println("No orders are in the queue");
					}
					else {
						System.out.println("Next order is: " + orderQueue.front());
					}
					System.out.println();
					break;
				
				case 4:
					if (orderQueue.isEmpty()) {
						System.out.println("No Orders to process");
					}
					else {
						System.out.println("Order Completed " + orderQueue.front());
						orderQueue.pop();
					}
					System.out.println();
					break;
					
				case 5:						//Views the size of the queue
					
					if (orderQueue.size() == 1) {
						System.out.println("There is " + orderQueue.size() + " order in the queue");
					}
					else {
						System.out.println("There are " + orderQueue.size() + " orders in the queue");
					}
					System.out.println();
					break;
					
				case 6:
					System.out.println("Ordering System exited");
					scnr.close();
					System.out.println();
					return;
				}	
			}
	}	
}
