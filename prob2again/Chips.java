package prob2again;

import java.util.Random;

public class Chips extends PerishableItem {
	public Chips(int id) {
		super(id, "Chips", randomExpireDays());
	}
	
	public static int randomExpireDays() {
		Random r = new Random();
        int expireInDays = r.nextInt(101);
        return expireInDays;
    }
}
