package codebyrj;


import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Objects;


public class dynamic_Queue<X> {				//<X> makes it generic so i don't have to make different classes for String, Int, Double or the custom "order" that we are creating
	private int frontIndex;
	private int backIndex;
	private int sizeVar;
	private int capacityVar = 4;
	private X[] arr;
	
	

	@SuppressWarnings("unchecked")			//Makes it so the arr is no longer highlighted for the warning nothing special.
	public dynamic_Queue(Class<X> clazz) {  // Clazz allows for 
		arr = (X[]) Array.newInstance(clazz, capacityVar);
		frontIndex = 0;
		backIndex = -1;
		sizeVar = 0;
	}
	
	public void prioritySort() {
		ArrayList<Objects> list = new ArrayList<>();
		for(int i=0; i<list.size();i++) {
			
		}
		
		Comparator<Objects> comp = new DateComparator<>();
		Collections.sort(list,comp);
	}
	
	public boolean isEmpty() {
		return sizeVar == 0;
	}
	
	public int size() {
		return sizeVar;
	}
	
	public X front() {
		if (isEmpty()) {
			System.out.println("The Queue is empty");
			return null;
		}
		return arr[frontIndex];
	}
	
	public void push(X x) {
		if (sizeVar == capacityVar) resize();
		if (isEmpty()) {
			frontIndex = backIndex = 0;				// Resets queue when last item is taken away 
		}
		else {
			backIndex = (backIndex + 1) % capacityVar;
			}
		arr[backIndex] = x;
		sizeVar++;
		prioritySort();
	}
		
		private void resize() {
			capacityVar *= 2;
			@SuppressWarnings("unchecked")
			X[] temp = (X[]) Array.newInstance(arr.getClass().getComponentType(), capacityVar);
			for (int i = 0, j = frontIndex; i < sizeVar; i++) {
				temp[i] = arr[j];
				j = (j + 1) % capacityVar;
			}
			frontIndex = 0;
			backIndex = sizeVar - 1;
			arr = temp;
		}

		public void pop() {
			if (isEmpty()) {
				System.out.println("Queue is empty");
			}
			if (frontIndex == backIndex) {
				frontIndex = backIndex = -1;
			}
			else {
				frontIndex = (frontIndex + 1) % capacityVar;
			}
			sizeVar--;
		}	
	}