import java.util.*;
public class CargoCompartment extends Compartment{
	
	Collection<CargoItem> content;
	CargoItem cargoItem;
	public CargoCompartment() {
		super();
		this.cargoItem=new CargoItem();
		// TODO Auto-generated constructor stub
	}
	
	public void addCargoItem() {
        CargoItem cargo=new CargoItem();
        content.add(cargo);
	}
	
	public int calculateProfit(){
		return content.size()*cargoItem.getProfit();
	}
	

	
	

}
