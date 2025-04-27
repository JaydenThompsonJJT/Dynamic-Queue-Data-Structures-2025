package prob2again;

import java.util.Random;

public class Rice extends PerishableItem {
	public Rice(int id) {
        super(id, "Rice", randomExpireDays());
    }
	
	public static int randomExpireDays() {
		Random r = new Random();
        int expireInDays = r.nextInt(101);
        return expireInDays;
    }
}
