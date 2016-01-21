package train;

import java.util.ArrayList;

public class Wagon {
	final int Price = 100;
	final int nrOfPassengers = 100;
	final String ID = "000";
	String Type="";
	ArrayList<Carriable> carry = new ArrayList<Carriable>();
	int Profit = 0;
		
	private void GiveType(int i){
		if (i==0) Type="Passenger";
		if (i==1) Type="Cargo";
	}
	
	public int ProfitComputation() {
		if (Type == "Passenger")
			Profit = Price * ((CharSequence) carry).length();
	
		if (Type=="Cargo")
			Profit = CargoItem.CargoItemProfit * carry.length();
		return Profit;
	}

	public boolean CheckIfFull() {
		if (nrOfPassengers >= carry.length())
			return true;
		else
			return false;
	}
	
}
