import java.util.Collection;

public class PassengerCompartment extends Compartment{

	Collection<Passenger> content;
	
	public PassengerCompartment() {
		super();
	}
	
	public int calculateProfit(){
		return passengerTicketPrice*content.size();
		
	}
	
	public void addPassenger() {
		if (content.size()<= maxNrOfPassengers){
	
	       Passenger passenger=new Passenger();
		   content.add(passenger);
		   
		}
		else
			System.out.println("The compartment is full");
	       
		}
	

}
