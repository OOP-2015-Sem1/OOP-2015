package colocviu;

import java.util.ArrayList;


public class airport{

	ArrayList<plane> al = new ArrayList<plane>();
	

	public void addPlane(plane airplane) {
		if (airplane != null)
			al.add(airplane);
	}

	public void removePlane(plane airplane) {
		if (al != null)
			al.remove(airplane);
	}

	public boolean checkPlane(plane airplane) {
		boolean check = false;
		for (int i = 0; i < al.size(); i++)
			if (al.get(i) != airplane) {
				check = true;
			}
		if (check)
			return true;
		else
			return false;
	}

}
