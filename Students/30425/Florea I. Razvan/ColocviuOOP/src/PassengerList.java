import java.util.ArrayList;

public class PassengerList {

	private ArrayList<Passenger> passengerList = new ArrayList<Passenger>();
	Passenger p1 = new Passenger("Mike");
	Passenger p2 = new Passenger("Sue");
	Passenger p3 = new Passenger("John");
	Passenger p4 = new Passenger("Emily");
	Passenger p5 = new Passenger("Bod");

	public PassengerList() {
		passengerList.add(p1);
		passengerList.add(p2);
		passengerList.add(p3);
		passengerList.add(p4);
		passengerList.add(p5);

	}

	public ArrayList<Passenger> getPassengerList() {
		return passengerList;
	}

}
