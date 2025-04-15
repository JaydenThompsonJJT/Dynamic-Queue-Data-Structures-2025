// Jayden Thompson - PerishableItem
package prob2;

import Item;
import java.util.Random;

public class PerishableItem extends Item {
	private int expireInDays;
	
	public PerishableItem(int id, String name) {
		super(id, name);
        this.expireInDays = new Random().nextInt(101);
	}
	
	@Override
	public boolean isPerishable() {
		return true;
	}
	
	public int getExpireInDays() {
		return expireInDays;
	}
	
	public boolean isExpired(int currentDay) {
		return expireInDays - currentDay <= 0;
	}
}