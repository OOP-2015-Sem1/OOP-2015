
public class PassengerWagon extends Wagon{
	
	final public int price = 100;
	final private int maxPass;
	
	public PassengerWagon(String UUID, int maxPass){
		super(UUID, 100);
		this.maxPass = maxPass;
	}
	
	public int calcProfit(){
		return price * super.carry.size();
	}
	
	public void addPassenger(Passenger pass){
		if (super.carry.size()<maxPass)
		super.carry.add(pass);
	}
}
