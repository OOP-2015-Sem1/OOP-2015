package airport1;

import java.util.ArrayList;
import java.util.Random;

public class Airport {
	
	ArrayList<Plane> planes= new ArrayList<Plane>();
	private int nrPlanes  = Math.abs(new Random().nextInt(50));
	public Airport(){
		
	}
	private boolean checkIfPlaneInStation()
	{
		return true;
	}
	
}
