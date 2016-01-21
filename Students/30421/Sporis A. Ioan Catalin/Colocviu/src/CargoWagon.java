import java.util.Random;
import java.util.UUID;

public class CargoWagon<T> extends Wagon<T> {
	private CargoItem cargoItem = new CargoItem(UUID.randomUUID().toString(),new Random().nextInt(100));
	public CargoWagon(int ID) {
		super(ID);
	}
	public void computeProfit (){
		this.setProfit(this.getCollection().size()*this.cargoItem.getProfit());
	}
	public CargoItem returnCarriable (){
		Random rndm = new Random();
		return new CargoItem(UUID.randomUUID().toString(),rndm.nextInt(100));
	}
	@Override
	public void addCarriables() {
		
	}
	
}
