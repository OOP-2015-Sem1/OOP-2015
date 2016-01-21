import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ShipBay {
	int r=0;
	private boolean yes;
	public ShipBay() {
		Set<Integer> ship=new HashSet<Integer>();
		r=new Random().nextInt(20);
		Math.abs(r);
		ship.add(r);
		
		if(ship.contains(ship))
		{
			setYes(true);
		}else
		{
			setYes(false);
		}
		if(ship.contains(ship))
		{
			ship.remove(ship);
		}
		if(r==new Random().nextInt())
		{
			ship.add(r);
		}
	}
	public boolean isYes() {
		return yes;
	}
	public void setYes(boolean yes) {
		this.yes = yes;
	}
}
