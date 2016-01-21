package pachet.colocviu;

public class ShipBay {

	public void receivingShip(){
		
	}

	public void departingShip(){
		
	}
	
	public boolean checkingShip(){
		return true ;
		
	}
	public ShipBay(){
		Ship first = new Ship();
		first.name="My first ship";
		first.type="passenger";
		
		Ship second = new Ship();
		second.name="My second ship";
		second.type="cargo";
	}
}
