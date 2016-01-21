import java.util.*;

public class Compartment<Carriable>  {
public static final int MAX_NR_Of_PASSAGERS= 50;
public static final int PASSANGER_PRICE= 100;

Collection<Carriable> cargo;
Collection<Carriable> passengers;
private int finalPrice;

	public Compartment(Collection<Carriable> passengers){
		 final String ID= UUID.randomUUID().toString();		 
		 if (passengers.getClass()== Passenger.class){
			 cargo.clear();
			 this.passengers= passengers;
			 setFinalPrice(getProfitOfPeople());
		 }else{
			 passengers.clear();
			 this.cargo= passengers;
			 setFinalPrice(getProfitOfCargo());			 
		 }
		 
	}
	public int getProfitOfPeople(){
		int profit=0;
		for(Carriable s:passengers){
			profit=profit+PASSANGER_PRICE;
		}
		return profit;
	}
	public int getProfitOfCargo(){
		int profit=0;
		profit=profit+ cargo.size()*10;
		
		return profit;
	}
	public int getFinalPrice() {
		if (cargo.size()==0){
			return getProfitOfPeople();
		}else{
			return getProfitOfCargo();
		}
		
	}
	public void setFinalPrice(int finalPrice) {
		this.finalPrice = finalPrice;
	}
	
	public void addCarriables(Carriable passenger){
		 if (passengers.getClass()== Passenger.class){
			 if(passengers.size()==MAX_NR_Of_PASSAGERS){
				 System.out.println("Compartimentul e full Sorry");
			 }
			 else{
				 passengers.add(passenger);
			 }
		 }else{
			 //which here is an item
			 cargo.add(passenger);
		 }
	}

}
