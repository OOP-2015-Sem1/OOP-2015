package pack;

import java.util.Comparator;

public class SortByName implements Comparator{

	@Override
	public int compare(Object arg0, Object arg1) {
		
		Train train0= (Train) arg0;
		Train train1= (Train) arg1;
		
		return train0.getName().compareTo(train1.getName());
	}

}
