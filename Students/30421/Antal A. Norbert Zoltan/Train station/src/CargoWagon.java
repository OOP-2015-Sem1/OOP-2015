
public class CargoWagon extends Wagon{
	
	private CargoItem ci;
	
	public CargoWagon(String UUID, int maxPass, CargoItem ci){
		super(UUID, maxPass);
		this.ci = ci;
	}
	
	public int calcProfit(){
		return super.carry.size()*ci.profit;
	}
	
}
