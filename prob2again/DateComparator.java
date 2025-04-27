package prob2again;

import java.util.Comparator;

public class DateComparator implements Comparator<Item> {

	@Override
    public int compare(Item o1, Item o2) {
        if(o1 instanceof PerishableItem && o2 instanceof PerishableItem) {
            PerishableItem p1 = (PerishableItem)o1;
            PerishableItem p2 = (PerishableItem)o2;
            return Integer.compare(p1.getExpireInDays(), p2.getExpireInDays());
        }
        return 0;
    }
}
