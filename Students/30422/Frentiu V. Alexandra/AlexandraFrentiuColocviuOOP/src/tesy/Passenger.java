package tesy;

public class Passenger extends Compartment implements Carriable  {
 public   String name;
 final int price = 100;
	public Passenger(String compName) {
		super(compName);
		
	}
	protected int calculateProfit()
	{
		return price * super.countPass;
	}

}
