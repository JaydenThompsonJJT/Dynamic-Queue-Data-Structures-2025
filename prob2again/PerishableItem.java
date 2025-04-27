// Jayden Thompson - PerishableItem class
package prob2again;

import java.util.Random;

public class PerishableItem extends Item {
	private int expireInDays;
	
	public PerishableItem(int id, String name, int expireInDays) {
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
	
	public boolean isExpired() {
		return expireInDays <= 0;
	}
	
	public void decreaseDays(int days) {
		this.expireInDays -= days;
	}
	
	public double getPrice() {
		if(expireInDays <= 0) {
			return 0.0;
		}
		return super.getPrice();
	}
}