package codebyrj;

import java.util.Comparator;

public class DateComparator<T, Product> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		Product p1 = (Product)o1;
		Product p2 = (Product)o2;
		if(p1.getDate().substring(6,8)>p1.getDate().substring(6,8)) {
			return 1;
		}
		else if(p1.getDate().substring(6,8)<p1.getDate().substring(6,8)){
			return-1;
		}
		else {
			if(p1.getDate().substring(3,5)>p1.getDate().substring(3,5)) {
				return 1;
			}
			else if(p1.getDate().substring(3,5)<p1.getDate().substring(3,5)) {
				return -1;
			}
			else {
				if(p1.getDate().substring(0,3)>p1.getDate().substring(0,3)) {
					return 1;
				}
				else {
					return -1;
				}
			}
		}
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		return 0;
	}

}
