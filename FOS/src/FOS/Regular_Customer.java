package FOS;
import java.util.*;

public class Regular_Customer {
    private Map<String, Customer> customers;
	    public Regular_Customer() {
        customers = new HashMap<>();
    }
	    public void addCustomer(String name, order previousOrder) {
        if (!customers.containsKey(name)) {
            customers.put(name, new Customer(name));
        }
	        customers.get(name).addOrder(previousOrder);
   }
	    public List<order> getPreviousOrders(String name) {
	        if (customers.containsKey(name)) {
            return customers.get(name).getOrders();
        }
      return new ArrayList<>();
	    }
	    public boolean isRegularCustomer(String name) {
       return customers.containsKey(name);
	    }
	}

	class Customer {
    private String name;
    private List<order> orderHistory;
	    public Customer(String name) {
       this.name = name;
	        orderHistory = new ArrayList<>();
    }
	    public void addOrder(order newOrder) {
        orderHistory.add(newOrder);
    }
	    public List<order> getOrders() {
        return orderHistory;
    }
}
