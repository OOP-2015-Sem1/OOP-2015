
public class PassengerWagon<Passenger> extends Wagon<Passenger> {
	private final int maxNumber = 100;
	private final int price = 100;
	public PassengerWagon(int ID){
		super(ID);
	}
	
	public void computeProfit(){
		int size;
		size = this.getCollection().size();
		this.setProfit(price * size);
	}

	@Override
	public void addCarriables() {
		// TODO Auto-generated method stub
		
	}
	public void addPessengers(Passenger passenger){
		if(this.carriables.size()<maxNumber){
			this.carriables.add(passenger);
		}
	}

	
}
