
public class Passenger implements Carriable{
	final String name;
	final private int price = 100;
	
	public Passenger(String name){
		this.name = name;
	}
	
	public int getPrice(){
		return this.price;
	}
}
